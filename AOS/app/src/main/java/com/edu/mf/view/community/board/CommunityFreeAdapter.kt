package com.edu.mf.view.community.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edu.mf.databinding.ItemFragmentCommunityFreeBinding
import com.edu.mf.repository.model.community.BoardListItem
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.detail.CommunityDetailFragment
import com.edu.mf.viewmodel.CommunityViewModel

class CommunityFreeAdapter(
    private val mainActivity: MainActivity,
    private val communityViewModel: CommunityViewModel,
    private val boardList: List<BoardListItem>
    ): RecyclerView.Adapter<CommunityFreeAdapter.FreeAdapter>() {
    private lateinit var binding: ItemFragmentCommunityFreeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreeAdapter {
        binding = ItemFragmentCommunityFreeBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent
            , false
        )
        communityViewModel.getBoardList(boardList)
        return FreeAdapter(binding)
    }

    override fun onBindViewHolder(holder: FreeAdapter, position: Int) {
        holder.bind(boardList[position])
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    inner class FreeAdapter(
        private val binding: ItemFragmentCommunityFreeBinding
        ): RecyclerView.ViewHolder(binding.root){
            fun bind(boardItem: BoardListItem){
                binding.boardItem = boardItem
                binding.cardviewItemFragmentCommunityFree.setOnClickListener {
                    CommunityBoardFragment.rViewItemPosition = layoutPosition
                    mainActivity.addFragment(CommunityDetailFragment(boardItem))
                }

                if (CommunityBoardFragment.myBoard){
                    binding.textviewItemFragmentCommunityFreeWriter.visibility = View.GONE
                } else{
                    binding.textviewItemFragmentCommunityFreeWriter.visibility = View.VISIBLE
                }
            }
        }
}