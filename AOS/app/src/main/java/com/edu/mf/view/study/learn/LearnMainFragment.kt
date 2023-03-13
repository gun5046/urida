package com.edu.mf.view.study.learn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.FragmentLearnMainBinding
import com.edu.mf.utils.App
import com.edu.mf.viewmodel.MainViewModel


class LearnMainFragment : Fragment() {

    private lateinit var binding : FragmentLearnMainBinding
    private lateinit var viewmodel : MainViewModel
    private val TAG = "LearnMainFragment_지훈"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_learn_main,container,false)
        viewmodel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding.apply {
            binding.list = App.PICTURES[viewmodel.selectedCategory]
            binding.vm = viewmodel
        }
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewmodel.setCurrentIndex(0)
        viewmodel.setCurrentAnswer(App.PICTURES[viewmodel.selectedCategory][0])
        viewmodel.currentIndex.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "onViewCreated: ${it}")
            viewmodel.changeBookMark("${it+1}/${App.PICTURES[viewmodel.selectedCategory].size}")
            binding.imageviewFragmentLearnMainImage.setImageResource(resources.getIdentifier(
                "pictures_${viewmodel.selectedCategory}_${it}","drawable",requireActivity().packageName))
            viewmodel.setCurrentAnswer(App.PICTURES[viewmodel.selectedCategory][it])
        })

    }

    private fun init(){

    }


    
}