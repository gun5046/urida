package com.edu.mf.view.study.quiz

import android.provider.ContactsContract.Data
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.edu.mf.R
import com.edu.mf.databinding.ItemFragmentLearnBinding
import com.edu.mf.databinding.ItemFragmentQuizBinding
import com.edu.mf.utils.App
import com.edu.mf.utils.SharedPreferencesUtil


class QuizAdapter(

) : RecyclerView.Adapter<QuizAdapter.LearnViewHolder>(){
    private val datas = ArrayList<String>()
    private lateinit var quizClickListener: QuizClickListener
    private var flag = -1
    inner class LearnViewHolder(val binding:ItemFragmentQuizBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data:String){
            binding.imageviewItemIcon.setImageResource(binding.root.resources.getIdentifier("ic_word_icon_${layoutPosition}","drawable",binding.root.context.packageName))
            binding.data = data
            binding.position = layoutPosition+1
            binding.buttonItemQuizStart.setOnClickListener {view->
                quizClickListener.onClick(view,layoutPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemFragmentQuizBinding>(inflater,R.layout.item_fragment_quiz,parent,false)


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
    interface QuizClickListener{
        fun onClick(view: View, position:Int)
    }
    fun setOnQuizClickListener(quizClickListener: QuizClickListener){
        this.quizClickListener = quizClickListener
    }
}