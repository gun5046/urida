package com.edu.mf.view.community.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.databinding.ItemFragmentCommunityFreeBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.detail.CommunityDetailFragment

class CommunityFreeAdapter(
    private val mainActivity: MainActivity
    ): RecyclerView.Adapter<CommunityFreeAdapter.FreeAdapter>() {
    private lateinit var binding: ItemFragmentCommunityFreeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreeAdapter {
        binding = ItemFragmentCommunityFreeBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent
            , false
        )
        return FreeAdapter(binding)
    }

    override fun onBindViewHolder(holder: FreeAdapter, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class FreeAdapter(
        private val binding: ItemFragmentCommunityFreeBinding
        ): RecyclerView.ViewHolder(binding.root){
            fun bind(){
                binding.cardviewItemFragmentCommunityFree.setOnClickListener {
                    mainActivity.addFragment(CommunityDetailFragment())
                }
            }
        }
}