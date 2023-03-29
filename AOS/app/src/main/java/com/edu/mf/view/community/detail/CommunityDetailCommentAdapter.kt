package com.edu.mf.view.community.detail

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemFragmentCommunityDetailCommentBinding
import com.edu.mf.repository.model.community.CommentListItem
import com.edu.mf.viewmodel.CommunityViewModel

class CommunityDetailCommentAdapter(
    private val communityViewModel: CommunityViewModel,
    private val commentList: List<CommentListItem>
): RecyclerView.Adapter<CommunityDetailCommentAdapter.CommentViewHolder>() {
    private lateinit var binding:ItemFragmentCommunityDetailCommentBinding

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
                binding.imageviewItemFragmentCommunityDetailDots.setOnClickListener {
                    makeDialog()
                }
            }

            // 다이얼로그 만들기
            private fun makeDialog(){
                val menuItems: Array<String> = arrayOf(
                    binding.root.resources.getString(R.string.fragment_community_detail_comment_dialog_edit)
                    , binding.root.resources.getString(R.string.fragment_community_detail_comment_dialog_delete)
                )

                val dialog = AlertDialog.Builder(binding.root.context, R.style.CommunityDialog)
                dialog.apply {
                    setItems(menuItems, DialogInterface.OnClickListener() { dialogInterface, position ->
                        println("#### $position")
                    })
                    show()
                }
            }
        }
}