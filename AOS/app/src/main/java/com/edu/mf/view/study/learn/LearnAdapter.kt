package com.edu.mf.view.study.learn

import android.content.Context
import android.provider.ContactsContract.Data
import android.text.BoringLayout.Metrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemFragmentLearnBinding
import com.edu.mf.utils.App
import com.edu.mf.utils.SharedPreferencesUtil


class LearnAdapter(

) : RecyclerView.Adapter<LearnAdapter.LearnViewHolder>(){
    private val datas = ArrayList<String>()
    private lateinit var categoryClickListener : CategoryClickListener
    private var flag = -1
    inner class LearnViewHolder(val binding:ItemFragmentLearnBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data:String){
            binding.data = data
            binding.flag = flag
            binding.imageviewItemIcon.setImageResource(binding.root.resources.getIdentifier("ic_word_icon_${layoutPosition}","drawable",binding.root.context.packageName))
            binding.position = layoutPosition+1
            binding.currentIndex = when(layoutPosition){
                0->SharedPreferencesUtil(binding.root.context).getFruitsBookMark()
                1->SharedPreferencesUtil(binding.root.context).getJobsBookMark()
                2->SharedPreferencesUtil(binding.root.context).getAnimalsBookMark()
                3->SharedPreferencesUtil(binding.root.context).getObjectsBookMark()
                4->SharedPreferencesUtil(binding.root.context).getPlacesBookMark()
                else->SharedPreferencesUtil(binding.root.context).getActionsBookMark()
            }
            binding.lastIndex = App.PICTURES[layoutPosition].size
            binding.progress = when(layoutPosition){
                0->SharedPreferencesUtil(binding.root.context).getFruitsBookMark()
                1->SharedPreferencesUtil(binding.root.context).getJobsBookMark()
                2->SharedPreferencesUtil(binding.root.context).getAnimalsBookMark()
                3->SharedPreferencesUtil(binding.root.context).getObjectsBookMark()
                4->SharedPreferencesUtil(binding.root.context).getPlacesBookMark()
                else->SharedPreferencesUtil(binding.root.context).getActionsBookMark()
            }+1 / App.PICTURES[layoutPosition].size * 100

            binding.constraintItemLearn.setOnClickListener {view->
                categoryClickListener.onClick(view,layoutPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemFragmentLearnBinding>(inflater,R.layout.item_fragment_learn,parent,false)


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
    interface CategoryClickListener{
        fun onClick(view: View, position:Int)
    }
    fun setOnCategoryClickListener(categoryClickListener: CategoryClickListener){
        this.categoryClickListener = categoryClickListener
    }
    fun setFlag(flag:Int){
        this.flag = flag
    }
}