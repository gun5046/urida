package com.edu.mf.view.community.chip

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.databinding.ItemFragmentCommunityMycommentBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.repository.model.community.BoardInfo
import com.edu.mf.repository.model.community.BoardListItem
import com.edu.mf.repository.model.community.MyCommentResponse
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.detail.CommunityDetailFragment
import com.edu.mf.viewmodel.CommunityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "CommunityMyCommentAdapt"
class CommunityMyCommentAdapter(
    private val mainActivity: MainActivity,
    private val communityViewModel: CommunityViewModel,
    private val communityService: CommunityService,
    private val commentList: List<MyCommentResponse>,
    private val tabPosition: Int
    ): RecyclerView.Adapter<CommunityMyCommentAdapter.MyCommentAdapter>() {
    private lateinit var binding: ItemFragmentCommunityMycommentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCommentAdapter {
        binding = ItemFragmentCommunityMycommentBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent
            , false
        )
        return MyCommentAdapter(binding)
    }

    override fun onBindViewHolder(holder: MyCommentAdapter, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    inner class MyCommentAdapter(
        private val binding: ItemFragmentCommunityMycommentBinding
        ): RecyclerView.ViewHolder(binding.root){
        fun bind(commentItem: MyCommentResponse){
            binding.myCommentItem = commentItem
            binding.cardviewItemFragmentCommunityMycomment.setOnClickListener {
                getCommentBoardInfo(commentItem.boardId)
            }
        }
    }

    // 게시글 정보 불러오기
    fun getCommentBoardInfo(boardId: Int){
        communityService.getBoardInfo(boardId)
            .enqueue(object : Callback<BoardInfo> {
                override fun onResponse(call: Call<BoardInfo>, response: Response<BoardInfo>) {
                    if (response.code() == 200){
                        val body = response.body()!!
                        val boardListItem = BoardListItem(
                            body.boardId,
                            body.title,
                            body.content,
                            tabPosition,
                            body.view,
                            body.image,
                            body.dateTime,
                            body.likeCnt,
                            body.commentCnt,
                            body.nickname
                        )
                        communityViewModel.setBoardItem(boardListItem)
                        mainActivity.addFragment(CommunityDetailFragment(boardListItem))
                    }
                }

                override fun onFailure(call: Call<BoardInfo>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }
}