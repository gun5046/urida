package com.edu.mf.view.study.learn

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemDialogFragmentSelectBinding
import com.edu.mf.repository.model.study.PCategory

class LearnDialogAdapter():
    RecyclerView.Adapter<LearnDialogAdapter.LearnDialogViewHolder>(){

    private val datas = ArrayList<PCategory>()
    private lateinit var learnDialogListener: OnClickLearnDialogListener
    inner class LearnDialogViewHolder(val binding : ItemDialogFragmentSelectBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : PCategory){
            binding.textviewItemDialogTitle.text = data.title
            binding.textivewItemDialogSubtitle.text = data.sub_title
            if(data.isClicked){
                binding.itemDialogSelect.setBackgroundResource(R.drawable.learn_list_gray_10)
            }else{
                binding.itemDialogSelect.setBackgroundResource(R.drawable.learn_list_black_10)
            }
            binding.itemDialogSelect.setOnClickListener {
                learnDialogListener.onClick(it,layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnDialogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDialogFragmentSelectBinding>(inflater, R.layout.item_dialog_fragment_select,parent,false)


        return LearnDialogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LearnDialogViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
    fun setData(list:ArrayList<PCategory>){
        datas.clear()
        datas.addAll(list)
    }
    interface OnClickLearnDialogListener{
        fun onClick(view: View, position:Int)
    }
    fun setOnDialogClickListener(onClickLearnDialogClickListener : OnClickLearnDialogListener){
        this.learnDialogListener = onClickLearnDialogClickListener
    }
}