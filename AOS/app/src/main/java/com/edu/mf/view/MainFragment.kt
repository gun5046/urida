package com.edu.mf.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.mf.databinding.FragmentMainBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.CommunityFragment
import com.edu.mf.view.drawing.DrawingFragment
import com.edu.mf.view.picture.PictureFragment
import com.edu.mf.view.study.StudyFragment

class MainFragment: Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            cardviewStudy.setOnClickListener {
                mainActivity.addFragment(StudyFragment())
            }

            cardviewDrawing.setOnClickListener {
                mainActivity.addFragment(DrawingFragment())
            }

            cardviewPicture.setOnClickListener {
                mainActivity.addFragment(PictureFragment())
            }

            cardviewCommunity.setOnClickListener {
                mainActivity.addFragment(CommunityFragment())
            }
        }
    }
}