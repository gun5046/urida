package com.edu.mf.view.study.reslove

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemFragmentResolveBinding
import com.edu.mf.repository.model.resolve.ResolveResponse
import com.edu.mf.utils.App

class ResolveAdapter() : RecyclerView.Adapter<ResolveAdapter.ResolveAdapterHolder>() {

    private lateinit var resolveClickListener : ResolveClickListener

    private var datas : List<ResolveResponse> = emptyList()

    inner class ResolveAdapterHolder(val binding : ItemFragmentResolveBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : ResolveResponse){
            binding.resolve = data
            binding.buttonItemFragmentResolveAnswer.setOnClickListener {view->
                resolveClickListener.onClick(view,layoutPosition,data)
            }
            binding.textviewItemFragmentResolveTitle.text = App.categories[data.category_id]
            binding.textviewItemFragmentResolveCategory.text = App.pCategories[data.type].title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResolveAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemFragmentResolveBinding>(
            inflater, R.layout.item_fragment_resolve,parent,false)
        return ResolveAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: ResolveAdapterHolder, position: Int) {
        holder.bind(datas[position])
    }

    interface ResolveClickListener{
        fun onClick(view: View,position:Int,data:ResolveResponse)
    }
    fun setOnResolveClickListener(resolveClickListener: ResolveClickListener){
        this.resolveClickListener = resolveClickListener
    }

    override fun getItemCount(): Int {
        return datas.size
    }
    fun setData(list : List<ResolveResponse>){
        datas = list
    }

}