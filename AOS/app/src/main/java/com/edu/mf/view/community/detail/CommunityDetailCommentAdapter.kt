package com.edu.mf.view.community.detail

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemFragmentCommunityDetailCommentBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.repository.model.User
import com.edu.mf.repository.model.community.CommentListItem
import com.edu.mf.view.common.LoadingDialog
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.CommunityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "CommunityDetailCommentA"
class CommunityDetailCommentAdapter(
    private val mainActivity: MainActivity,
    private val communityDetailFragment: CommunityDetailFragment,
    private val commentEdittext: EditText,
    private val communityService: CommunityService,
    private val communityViewModel: CommunityViewModel,
    private var commentList: MutableList<CommentListItem>,
    private val user: User
): ListAdapter<CommentListItem, CommunityDetailCommentAdapter.CommentViewHolder>(
    CommentDiffCallback()
) {
    private lateinit var binding:ItemFragmentCommunityDetailCommentBinding
    private val loadingDialog = LoadingDialog()

    companion object{
        var itemPosition: Int = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        binding = ItemFragmentCommunityDetailCommentBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent
            , false
        )
        communityViewModel.getCommentList(commentList)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    inner class CommentViewHolder(
        private val binding: ItemFragmentCommunityDetailCommentBinding
        ): RecyclerView.ViewHolder(binding.root){
            fun bind(commentItem: CommentListItem){
                binding.commentItem = commentItem

                communityViewModel.commentItem.observe(communityDetailFragment.viewLifecycleOwner){
                    chkMyComment(it, adapterPosition)
                }
                communityViewModel.getCommentItem(commentItem)
                binding.executePendingBindings()
            }
        }

    // 내가 작성한 댓글인지 확인
    private fun chkMyComment(commentItem: CommentListItem, position: Int){
        if(commentItem.uid != user.uid){
            binding.imageviewItemFragmentCommunityDetailDots.visibility = View.GONE
        } else{
            binding.imageviewItemFragmentCommunityDetailDots.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    makeDialog(commentItem, position)
                }
            }
        }
    }

    // 다이얼로그 만들기
    private fun makeDialog(commentItem: CommentListItem, position: Int){
        val menuItems: Array<String> = arrayOf(
            binding.root.resources.getString(R.string.fragment_community_detail_comment_dialog_edit)
            , binding.root.resources.getString(R.string.fragment_community_detail_comment_dialog_delete)
        )

        val dialog = AlertDialog.Builder(binding.root.context, R.style.CommunityDialog)
        dialog.apply {
            setItems(menuItems, DialogInterface.OnClickListener() { dialogInterface, dialogPos ->
                itemPosition = position

                if (dialogPos == 0){  // 수정
                    communityDetailFragment.showKeyboard()
                    CommunityDetailFragment.commentUpdate = true
                    commentEdittext.setText(commentItem.content)
                    communityViewModel.getCommentItem(commentItem)
                } else{  // 삭제
                    changeLoadingState(true)
                    deleteComment(commentItem)
                }
            })
            show()
        }
    }

    // 댓글 삭제
    private fun deleteComment(commentItem: CommentListItem){
        communityService.deleteComment(commentItem.commentId)
            .enqueue(object : Callback<Int>{
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.code() == 200){
                        deleteNotify()
                    }
                    changeLoadingState(false)
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 댓글 수정 후 리사이클러 뷰 갱신
    fun updateNotify(commentItem: CommentListItem){
        commentList[itemPosition] = commentItem
        communityDetailFragment.getBoardInfo()
        this.notifyItemChanged(itemPosition)
    }

    // 댓글 삭제 후 리사이클러뷰 갱신
    private fun deleteNotify(){
        commentList.removeAt(itemPosition)
        communityDetailFragment.getCommentList()
        communityDetailFragment.getBoardInfo()
        this.notifyItemRemoved(itemPosition)
    }

    // 로딩화면 띄우고 없애기
    private fun changeLoadingState(state: Boolean){
        if(!state){
            loadingDialog.dismiss()
        } else{
            loadingDialog.show(mainActivity.supportFragmentManager, null)
        }
    }
}

class CommentDiffCallback: DiffUtil.ItemCallback<CommentListItem>(){
    override fun areItemsTheSame(oldItem: CommentListItem, newItem: CommentListItem): Boolean {
        return oldItem.commentId == newItem.commentId
    }

    override fun areContentsTheSame(oldItem: CommentListItem, newItem: CommentListItem): Boolean {
        return oldItem == newItem
    }
}