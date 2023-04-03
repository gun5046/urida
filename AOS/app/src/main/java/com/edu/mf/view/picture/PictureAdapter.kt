package com.edu.mf.view.picture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemMainPhotoFragBinding
import com.edu.mf.repository.model.Category

class PictureAdapter(val categories:List<Category>) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>(){
    private lateinit var pictureClickListener: PictureClickListener
    inner class PictureViewHolder(val binding : ItemMainPhotoFragBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : Category){
            binding.textviewItemTitle.text = data.title
            binding.textviewItemDescription.text = data.description
            binding.imageviewItemIcon.setImageResource(data.src)
            binding.constraintlayoutItemMainPhoto.setOnClickListener {
                pictureClickListener.onClick(layoutPosition,data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMainPhotoFragBinding>(layoutInflater, R.layout.item_main_photo_frag,parent,false)
        return PictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }
    interface PictureClickListener{
        fun onClick(position:Int,data:Category)
    }
    fun setOnPictureClickListener(pictureClickListener: PictureClickListener){
        this.pictureClickListener = pictureClickListener
    }
}