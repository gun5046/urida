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

class CommunityDetailCommentAdapter(
    //private val viewModel: CommunityViewModel
): RecyclerView.Adapter<CommunityDetailCommentAdapter.CommentViewHolder>() {
    private lateinit var binding:ItemFragmentCommunityDetailCommentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        binding = ItemFragmentCommunityDetailCommentBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent
            , false
        )
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class CommentViewHolder(
        private val binding: ItemFragmentCommunityDetailCommentBinding
        ): RecyclerView.ViewHolder(binding.root){
            fun bind(){
                binding.textviewItemFragmentCommunityDetailCommentNickname
                    .text = "닉네임 ${adapterPosition.toString()}"

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