package com.edu.mf.view.picture

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.edu.mf.databinding.ItemFragmentPictureGuideBinding
import com.edu.mf.databinding.ItemPictureResultBinding

class PictureGuideAdapter(
    val context: Context,
    val imageList: List<Int>,
    val stringList: List<String>
) : RecyclerView.Adapter<PictureGuideAdapter.PictureGuideViewHolder>() {

    inner class PictureGuideViewHolder(val binding: ItemFragmentPictureGuideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            Glide.with(binding.root).load(imageList[position]).into(binding.imageView)
            binding.textView.text = stringList[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureGuideViewHolder {
        return PictureGuideViewHolder(
            ItemFragmentPictureGuideBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PictureGuideViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}