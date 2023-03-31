package com.edu.mf.view.study

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edu.mf.databinding.ItemFragmentStudyGuideBinding

class StudyGuideAdapter(
    val context: Context,
    val imageList: List<Int>,
    val stringList: List<String>
) : RecyclerView.Adapter<StudyGuideAdapter.StudyGuideViewHolder>() {

    inner class StudyGuideViewHolder(val binding: ItemFragmentStudyGuideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            Glide.with(binding.root).load(imageList[position]).into(binding.imageView)
            binding.textView.text = stringList[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyGuideViewHolder {
        return StudyGuideViewHolder(
            ItemFragmentStudyGuideBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: StudyGuideViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}