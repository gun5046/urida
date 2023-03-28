package com.edu.mf.view.study.reslove

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemFragmentResolveBinding
import com.edu.mf.repository.model.resolve.ResolveResponse

class ResolveAdapter() : RecyclerView.Adapter<ResolveAdapter.ResolveAdapterHolder>() {

    private lateinit var resolveClickListener : ResolveClickListener

    private var datas : List<ResolveResponse> = emptyList()

    inner class ResolveAdapterHolder(val binding : ItemFragmentResolveBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : ResolveResponse){
            binding.resolve = data
            binding.buttonItemFragmentResolveAnswer.setOnClickListener {view->
                resolveClickListener.onClick(view,layoutPosition)
            }
            when(data.category_id){
                0->binding.textviewItemFragmentResolveTitle.text = "과일/채소"
                1->binding.textviewItemFragmentResolveTitle.text = "직업"
                2->binding.textviewItemFragmentResolveTitle.text = "동물"
                3->binding.textviewItemFragmentResolveTitle.text = "물체"
                4->binding.textviewItemFragmentResolveTitle.text = "장소"
                else->binding.textviewItemFragmentResolveTitle.text = "행동"
            }
            when(data.type){
                0->binding.textviewItemFragmentResolveCategory.text = "그림보고 단어 맞추기"
                1->binding.textviewItemFragmentResolveCategory.text = "낱말보고 그림 맞추기"
                2->binding.textviewItemFragmentResolveCategory.text = "빈칸에 알맞는 말 넣기"
                else->binding.textviewItemFragmentResolveCategory.text = "연관된 단어 맞추기"
            }
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
        fun onClick(view: View,position:Int)
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