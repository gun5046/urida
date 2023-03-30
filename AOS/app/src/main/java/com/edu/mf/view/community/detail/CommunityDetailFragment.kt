package com.edu.mf.view.community.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout.VERTICAL
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityDetailBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.repository.model.User
import com.edu.mf.repository.model.community.BoardInfo
import com.edu.mf.repository.model.community.BoardListItem
import com.edu.mf.repository.model.community.CommentListItem
import com.edu.mf.repository.model.community.CreateCommentData
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.CommunityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "CommunityDetailFragment"
class CommunityDetailFragment(
    private val boardItem: BoardListItem
    ): Fragment() {
    private lateinit var binding: FragmentCommunityDetailBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var communityService: CommunityService
    private lateinit var communityViewModel: CommunityViewModel
    private lateinit var user: User

    private var clickState = false

    companion object{
        var commentUpdate = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityDetailBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        communityService = mainActivity.communityService
        communityViewModel = CommunityViewModel()
        user = App.sharedPreferencesUtil.getUser()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentUpdate = false

        binding.communityDetail = this
        getBoardInfo()
        communityViewModel.boardListItem.observe(viewLifecycleOwner){
            binding.boardItem = it
        }
        clickBackPress()

        getLikeState()
        binding.imageviewFragmentCommunityDetailLikeEmpty.setOnClickListener {
            updateLikeState()
        }
        binding.imageviewFragmentCommunityDetailLikeFull.setOnClickListener {
            updateLikeState()
        }

        getCommentList()
        binding.buttonFragmentCommunityDetailWriteComment.setOnClickListener {
            if (!commentUpdate){
                writeComment()
            } else{
                updateComment(communityViewModel.commentItem.value!!)
            }
        }
        binding.imageviewFragmentCommunityDetailBack.bringToFront()
    }

    // 좋아요 상태 받아오기
    private fun getLikeState(){
        communityService.getLikeState(
            boardItem.boardId, user.uid!!
        ).enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() == 200){
                    val body = response.body()!!
                    clickState = body
                    changeLikeImgVisibility()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // 좋아요 상태 갱신
    private fun updateLikeState(){
        communityService.updateLikeState(
            boardItem.boardId, user.uid!!, clickState
        ).enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() == 200){
                    changeLikeState()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // 좋아요 이미지 변경
    private fun changeLikeImgVisibility(){
        if (!clickState){
            binding.imageviewFragmentCommunityDetailLikeEmpty
                .visibility = View.VISIBLE
            binding.imageviewFragmentCommunityDetailLikeFull
                .visibility = View.GONE
        } else{
            binding.imageviewFragmentCommunityDetailLikeEmpty
                .visibility = View.GONE
            binding.imageviewFragmentCommunityDetailLikeFull
                .visibility = View.VISIBLE
        }
    }

    // 좋아요 클릭 상태 변경
    private fun changeLikeState(){
        if (!clickState){
            setAnim(true)
            clickState = true
            getBoardInfo()
            changeLikeImgVisibility()
        } else{
            setAnim(false)
            clickState = false
            getBoardInfo()
            changeLikeImgVisibility()
        }
    }

    // 좋아요 애니메이션 지정
    private fun setAnim(likeState: Boolean){
        if (!likeState){
            val emptyAnim = AnimationUtils.loadAnimation(
                requireContext().applicationContext
                , R.anim.community_detail_faidin_down_empty
            )
            binding.imageviewFragmentCommunityDetailLikeEmpty
                .startAnimation(emptyAnim)

            val fullAnim = AnimationUtils.loadAnimation(
                requireContext().applicationContext
                , R.anim.community_detail_fadeout_down_full
            )
            binding.imageviewFragmentCommunityDetailLikeFull
                .startAnimation(fullAnim)
        } else{
            val emptyAnim = AnimationUtils.loadAnimation(
                requireContext().applicationContext
                , R.anim.community_detail_fadeout_up_empty
            )
            binding.imageviewFragmentCommunityDetailLikeEmpty
                .startAnimation(emptyAnim)

            val fullAnim = AnimationUtils.loadAnimation(
                requireContext().applicationContext
                , R.anim.community_detail_fadein_up_full
            )
            binding.imageviewFragmentCommunityDetailLikeFull
                .startAnimation(fullAnim)
        }
    }

    lateinit var commentAdapter: CommunityDetailCommentAdapter
    // 댓글 recyclerview 설정
    private fun setCommentAdapter(commentList: List<CommentListItem>){
        commentAdapter = CommunityDetailCommentAdapter(
            this,
            binding.edittextFragmentCommunityDetailWriteComment,
            communityService,
            communityViewModel,
            commentList.toMutableList(),
            user
        )
        binding.recyclerviewFragmentCommunityComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))
        }
    }

    // 댓글 목록 받기
    private fun getCommentList(){
        communityService.getCommentList(boardItem.boardId)
            .enqueue(object : Callback<List<CommentListItem>>{
            override fun onResponse(
                call: Call<List<CommentListItem>>,
                response: Response<List<CommentListItem>>
            ) {
                if(response.code() == 200){
                    val body = response.body()!!
                    setCommentAdapter(body)
                }
            }

            override fun onFailure(call: Call<List<CommentListItem>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // 댓글 작성
    private fun writeComment(){
        val commentData = CreateCommentData(
            binding.edittextFragmentCommunityDetailWriteComment.text.toString(),
            "",
            boardItem.boardId,
            user.uid!!
        )
        communityService.createComment(commentData)
            .enqueue(object : Callback<CommentListItem>{
            override fun onResponse(
                call: Call<CommentListItem>,
                response: Response<CommentListItem>
            ) {
                if (response.code() == 200){
                    binding.edittextFragmentCommunityDetailWriteComment.setText("")
                    hideKeyboard()

                    getBoardInfo()
                    getCommentList()
                }
            }

            override fun onFailure(call: Call<CommentListItem>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // 댓글 수정
    private fun updateComment(commentItem: CommentListItem){
        val commentData = CreateCommentData(
            binding.edittextFragmentCommunityDetailWriteComment.text.toString(),
            "",
            commentItem.boardId,
            user.uid!!
        )

        communityService.updateComment(commentItem.commentId, commentData)
            .enqueue(object : Callback<CommentListItem>{
                override fun onResponse(
                    call: Call<CommentListItem>,
                    response: Response<CommentListItem>
                ) {
                    if (response.code() == 200){
                        val body = response.body()!!
                        val newCommentItem = CommentListItem(
                            commentItem.commentId,
                            binding.edittextFragmentCommunityDetailWriteComment.text.toString(),
                            body.dateTime,
                            commentItem.boardId,
                            user.uid!!,
                            commentItem.nickname
                        )
                        commentAdapter.updateNotify(newCommentItem)
                        binding.edittextFragmentCommunityDetailWriteComment.setText("")
                        hideKeyboard()
                    }
                }

                override fun onFailure(call: Call<CommentListItem>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 게시글 내용 불러오기 (댓글 및 좋아요 수 갱신)
    fun getBoardInfo(){
        communityService.getBoardInfo(boardItem.boardId)
            .enqueue(object : Callback<BoardInfo>{
                override fun onResponse(call: Call<BoardInfo>, response: Response<BoardInfo>) {
                    if (response.code() == 200){
                        val body = response.body()!!
                        val boardListItem = BoardListItem(
                            body.boardId,
                            body.title,
                            body.content,
                            boardItem.categoryId,
                            boardItem.view,
                            body.dateTime,
                            body.likeCnt,
                            body.commentCnt,
                            body.nickname
                        )
                        communityViewModel.setBoardItem(boardListItem)
                    }
                }

                override fun onFailure(call: Call<BoardInfo>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 등록버튼 클릭 후 키보드 내리기
    private fun hideKeyboard(){
        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    // 댓글 다이얼로그 수정 버튼 클릭 시 키보드 올리기
    fun showKeyboard(){
        binding.edittextFragmentCommunityDetailWriteComment.requestFocus()
        val inputManager = requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(binding.edittextFragmentCommunityDetailWriteComment, 0)
    }

    // onBackPressed시 수정 상태 변경
    private fun clickBackPress(){
        if (commentUpdate){
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner, object : OnBackPressedCallback(true){
                    override fun handleOnBackPressed() {
                        commentUpdate = false
                    }
                })
        }
    }

    // 뒤로가기 아이콘 클릭 시
    fun backPressed(){
        mainActivity.popFragment()
    }
}