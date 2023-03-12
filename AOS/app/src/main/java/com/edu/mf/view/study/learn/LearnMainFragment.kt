package com.edu.mf.view.study.learn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.FragmentLearnMainBinding
import com.edu.mf.utils.App
import com.edu.mf.viewmodel.MainViewModel


class LearnMainFragment : Fragment() {

    private lateinit var binding : FragmentLearnMainBinding
    private lateinit var viewmodel : MainViewModel
    private lateinit var app:App
    private val TAG = "LearnMainFragment_지훈"
    private var currentIndex=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_learn_main,container,false)
        viewmodel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        app = App()
        binding.answer = App.PICTURES[viewmodel.selectedCategory][currentIndex]
        binding.current = currentIndex
        binding.list = App.PICTURES[viewmodel.selectedCategory]
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        setImageCategory()
    }

    /**
     * category별 이미지 리소스 저장
     */
    private fun setImageCategory(){
        Log.i(TAG, "setImageCategory:${viewmodel.selectedCategory},${viewmodel.selectedPCategory}")
        when(viewmodel.selectedCategory){
            0->{
                binding.imageviewFragmentLearnMainImage.setImageResource(resources.getIdentifier(
                    "fruits_0_${currentIndex}","drawable",requireActivity().packageName))
            }
            1->{
                binding.imageviewFragmentLearnMainImage.setImageResource(resources.getIdentifier(
                    "jobs_1_${currentIndex}","drawable",requireActivity().packageName))
            }
            2->{
                binding.imageviewFragmentLearnMainImage.setImageResource(resources.getIdentifier(
                    "animals_2_${currentIndex}","drawable",requireActivity().packageName))
            }
            3->{
                binding.imageviewFragmentLearnMainImage.setImageResource(resources.getIdentifier(
                    "objects_3_${currentIndex}","drawable",requireActivity().packageName))
            }
            4->{
                binding.imageviewFragmentLearnMainImage.setImageResource(resources.getIdentifier(
                    "places_4_${currentIndex}","drawable",requireActivity().packageName))
            }
            else->{
                binding.imageviewFragmentLearnMainImage.setImageResource(resources.getIdentifier(
                    "actions_5_${currentIndex}","drawable",requireActivity().packageName))
            }



        }


    }


}