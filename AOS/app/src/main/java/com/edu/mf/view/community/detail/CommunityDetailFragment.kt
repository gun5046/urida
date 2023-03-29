package com.edu.mf.view.community.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityDetailBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.repository.model.User
import com.edu.mf.repository.model.community.BoardListItem
import com.edu.mf.repository.model.community.CommentListItem
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.study.learn.LearnMainFragment
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

        binding.communityDetail = this
        binding.boardItem = boardItem

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLikeState()
        binding.imageviewFragmentCommunityDetailLikeEmpty.setOnClickListener {
            changeLikeState()
        }
        binding.imageviewFragmentCommunityDetailLikeFull.setOnClickListener {
            changeLikeState()
        }

        getCommentList()
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
                    changeLikeImgVisibility()
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
            updateLikeState()
            changeLikeImgVisibility()
        } else{
            setAnim(false)
            clickState = false
            updateLikeState()
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

    // 댓글 recyclerview 설정
    private fun setCommentAdapter(commentList: List<CommentListItem>){
        val commentAdapter = CommunityDetailCommentAdapter(communityViewModel, commentList)
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

    // 뒤로가기 아이콘 클릭 시
    fun backPressed(){
        mainActivity.popFragment()
    }
}