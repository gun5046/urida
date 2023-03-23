package com.edu.mf.view.community.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.databinding.ItemFragmentCommunityDrawingBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.detail.CommunityDetailFragment

class CommunityDrawingAdapter(
    private val mainActivity: MainActivity
    ) : RecyclerView.Adapter<CommunityDrawingAdapter.DrawingViewHolder>() {
    private lateinit var binding:ItemFragmentCommunityDrawingBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawingViewHolder {
        binding = ItemFragmentCommunityDrawingBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent
            , false
        )
        return DrawingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrawingViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class DrawingViewHolder(
        private val binding: ItemFragmentCommunityDrawingBinding
        ): RecyclerView.ViewHolder(binding.root){
            fun bind(){
                binding.cardviewItemFragmentCommunityDrawing.setOnClickListener {
                    mainActivity.addFragment(CommunityDetailFragment())
                }
            }
        }
}