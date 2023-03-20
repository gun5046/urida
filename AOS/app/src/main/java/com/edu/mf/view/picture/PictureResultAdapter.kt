package com.edu.mf.view.picture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.databinding.ItemPictureResultBinding
import com.edu.mf.repository.model.picture.DetectedPicture

class PictureResultAdapter(var PictureList: MutableList<DetectedPicture>): RecyclerView.Adapter<PictureResultAdapter.PictureResult>() {
    inner class PictureResult(val binding: ItemPictureResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int){
            binding.imagePictureResult.setImageBitmap(PictureList.get(position).bitmap)
            binding.textPictureResult.text = PictureList.get(position).label
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureResult {
        return PictureResult(ItemPictureResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PictureResult, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return PictureList.size
    }
}