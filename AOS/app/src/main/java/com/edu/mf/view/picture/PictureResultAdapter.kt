package com.edu.mf.view.picture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.databinding.ItemPictureResultBinding
import com.edu.mf.repository.model.picture.DetectedPicture
import com.edu.mf.viewmodel.MainViewModel

class PictureResultAdapter(var PictureList: MutableList<DetectedPicture>, val mainViewModel: MainViewModel): RecyclerView.Adapter<PictureResultAdapter.PictureResult>() {
    inner class PictureResult(val binding: ItemPictureResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int){
            val resultText = PictureList.get(position).label
            binding.layoutPictureResult.setOnClickListener {
                mainViewModel.startTTSWithParameter(resultText)
            }
            binding.imagePictureResult.setImageBitmap(PictureList.get(position).bitmap)
            binding.textPictureResult.text = resultText
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