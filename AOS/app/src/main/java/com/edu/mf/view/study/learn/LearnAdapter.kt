package com.edu.mf.view.study.learn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemFragmentLearnBinding


class LearnAdapter(

) : RecyclerView.Adapter<LearnAdapter.LearnViewHolder>(){
    private val datas = ArrayList<String>()

    class LearnViewHolder(val binding:ItemFragmentLearnBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data:String){
            binding.textviewItemLearnTitle.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFragmentLearnBinding.inflate(inflater,parent,false)

        return LearnViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LearnViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
    fun setList(lists:ArrayList<String>){
        datas.clear()
        datas.addAll(lists)
    }
}