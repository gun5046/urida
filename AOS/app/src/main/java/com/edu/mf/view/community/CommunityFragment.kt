package com.edu.mf.view.community

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.board.CommunityBoardAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class CommunityFragment: Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabLayout()

        binding.fabFragmentCommunity.setOnClickListener{
            mainActivity.addFragmentNoAnim(CommunityRegisterFragment())
        }
    }

    // tabLayout 설정
    private fun setTabLayout(){
        val tabTitleArr = arrayOf(
            R.string.fragment_community_tablayout_title_free
            , R.string.fragment_community_tablayout_title_drawing
        )
        val viewpager = binding.viewpagerFragmentCommunity
        val tabLayout = binding.tablayoutFragmentCommunity

        viewpager.adapter = CommunityBoardAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewpager){ tab, position ->
            tab.text = resources.getString(tabTitleArr[position])
        }.attach()

        tabLayout.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 -> binding.fabFragmentCommunity.show()
                    1 -> binding.fabFragmentCommunity.hide()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}