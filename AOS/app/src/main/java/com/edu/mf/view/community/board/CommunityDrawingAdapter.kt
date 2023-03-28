package com.edu.mf.view.community.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.databinding.ItemFragmentCommunityDrawingBinding
import com.edu.mf.repository.model.community.BoardListItem
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.detail.CommunityDetailFragment
import com.edu.mf.viewmodel.CommunityViewModel

class CommunityDrawingAdapter(
    private val mainActivity: MainActivity,
    private val communityViewModel: CommunityViewModel,
    private val boardList: List<BoardListItem>
    ) : RecyclerView.Adapter<CommunityDrawingAdapter.DrawingViewHolder>() {
    private lateinit var binding:ItemFragmentCommunityDrawingBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawingViewHolder {
        binding = ItemFragmentCommunityDrawingBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent
            , false
        )
        communityViewModel.getBoardList(boardList)
        return DrawingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrawingViewHolder, position: Int) {
        holder.bind(boardList[position])
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    inner class DrawingViewHolder(
        private val binding: ItemFragmentCommunityDrawingBinding
        ): RecyclerView.ViewHolder(binding.root){
            fun bind(boardItem: BoardListItem){
                binding.boardItem = boardItem
                binding.cardviewItemFragmentCommunityDrawing.setOnClickListener {
                    mainActivity.addFragment(CommunityDetailFragment(boardItem))
                }
            }
        }
}