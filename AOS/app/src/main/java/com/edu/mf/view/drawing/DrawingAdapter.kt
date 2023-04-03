package com.edu.mf.view.drawing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemMainDrawingFragBinding
import com.edu.mf.repository.model.Category

class DrawingAdapter(val categories:List<Category>) : RecyclerView.Adapter<DrawingAdapter.DrawingViewHolder>(){
    private lateinit var drawingClickListener: DrawingClickListener

    inner class DrawingViewHolder(val binding : ItemMainDrawingFragBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : Category){
            binding.textviewItemTitle.text = data.title
            binding.textviewItemDescription.text = data.description
            binding.imageviewItemIcon.setImageResource(data.src)
            binding.constraintlayoutMainFragDrawing.setOnClickListener {
                drawingClickListener.onClick(layoutPosition,data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMainDrawingFragBinding>(layoutInflater, R.layout.item_main_drawing_frag,parent,false)
        return DrawingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrawingViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    interface DrawingClickListener{
        fun onClick(position:Int,data:Category)
    }
    fun setOnClickDrawingClickListener(drawingClickListener: DrawingClickListener){
        this.drawingClickListener = drawingClickListener
    }
}