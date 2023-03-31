package com.edu.mf.view.study

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edu.mf.R
import com.edu.mf.databinding.FragmentStudyGuideBinding
import com.edu.mf.view.common.MainActivity

class StudyGuideFragment : Fragment() {

    private lateinit var binding: FragmentStudyGuideBinding
    private lateinit var mainActivity: MainActivity
    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = MainActivity.getInstance()!!
        binding = FragmentStudyGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = listOf(R.drawable.guide_picture_3, R.drawable.guide_picture_3, R.drawable.guide_picture_3)
        val stringList = listOf(
            requireContext().resources.getString(R.string.fragment_study_guide_first_text),
            requireContext().resources.getString(R.string.fragment_study_guide_second_text),
            requireContext().resources.getString(R.string.fragment_study_guide_third_text)
        )
        binding.viewPager.adapter = StudyGuideAdapter(requireContext(), imageList, stringList)
        binding.viewPager.isUserInputEnabled = false
        binding.circleIndicator.createIndicators(3, 0)
        binding.circleIndicator.setViewPager(binding.viewPager)

        binding.buttonStudyGuide.setOnClickListener {
            if(index < 2){
                index++
                binding.viewPager.setCurrentItem(index, true)
                if(index == 2){
                    binding.buttonStudyGuide.text = requireContext().resources.getString(R.string.fragment_study_guide_button_next)
                }
            } else {
                mainActivity.popFragment()
            }
        }
    }
}