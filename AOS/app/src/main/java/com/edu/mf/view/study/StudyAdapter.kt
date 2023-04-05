package com.edu.mf.view.study

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemMainWordFragBinding
import com.edu.mf.repository.model.Category

class StudyAdapter(val categories:List<Category>):RecyclerView.Adapter<StudyAdapter.StduyViewHolder>(){
    private lateinit var binding : ItemMainWordFragBinding
    private lateinit var studyClickListener: StudyClickListener
    inner class StduyViewHolder(val binding : ItemMainWordFragBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(category:Category){
            binding.textviewItemTitle.text = category.title
            binding.textviewItemDescription.text = category.description
            //binding.imageviewItemIcon.setImageResource(category.src)
            binding.constraintlayoutItemMainWord.setOnClickListener {
                studyClickListener.onClick(layoutPosition,category)
            }
            binding.myButton.setOnClickListener {
                studyClickListener.onClick(layoutPosition,category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StduyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        binding = DataBindingUtil.inflate<ItemMainWordFragBinding>(inflater, R.layout.item_main_word_frag,parent,false)
        return StduyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StduyViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }
    interface StudyClickListener{
        fun onClick(position:Int,data:Category)
    }
    fun setOnStudyClickListener(studyClickListener: StudyClickListener){
        this.studyClickListener = studyClickListener
    }

}