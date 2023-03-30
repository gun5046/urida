package com.edu.mf.view.community.detail

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemFragmentCommunityDetailCommentBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.repository.model.User
import com.edu.mf.repository.model.community.CommentListItem
import com.edu.mf.repository.model.community.CreateCommentData
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.CommunityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "CommunityDetailCommentA"
class CommunityDetailCommentAdapter(
    private val communityDetailFragment: CommunityDetailFragment,
    private val commentEdittext: EditText,
    private val communityService: CommunityService,
    private val communityViewModel: CommunityViewModel,
    private var commentList: MutableList<CommentListItem>,
    private val user: User
): RecyclerView.Adapter<CommunityDetailCommentAdapter.CommentViewHolder>() {
    private lateinit var binding:ItemFragmentCommunityDetailCommentBinding

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
        holder.bind(commentList[position], position)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    inner class CommentViewHolder(
        private val binding: ItemFragmentCommunityDetailCommentBinding
        ): RecyclerView.ViewHolder(binding.root){
            fun bind(commentItem: CommentListItem, position: Int){
                binding.commentItem = commentItem
                communityViewModel.getCommentItem(commentItem)
                chkMyComment(commentItem, position)
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
        this.notifyItemRemoved(itemPosition)
        commentList.removeAt(itemPosition)
        communityDetailFragment.getBoardInfo()
    }
}