package com.edu.mf.view.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.board.CommunityBoardAdapter
import com.edu.mf.view.community.board.CommunityFreeFragment
import com.edu.mf.view.community.chip.CommunityLikeFragment
import com.edu.mf.view.community.chip.CommunityMyBoardFragment
import com.edu.mf.view.community.chip.CommunityMyCommentFragment
import com.edu.mf.view.community.chip.CommunityMypageAdapter
import com.edu.mf.viewmodel.CommunityViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

private const val TAG = "CommunityFragment"
class CommunityFragment: Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var communityService: CommunityService
    private lateinit var communityViewModel: CommunityViewModel
    //private lateinit var tabLayout : TabLayout
    //private var tabPosition = 0

    companion object{
        var tabPosition = 0
        lateinit var tabLayout : TabLayout
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        communityService = mainActivity.communityService
        communityViewModel = CommunityViewModel()

        

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabLayout()
        clickChip()

        binding.fabFragmentCommunity.setOnClickListener{
            //mainActivity.addFragmentNoAnimMypage(CommunityMypageFragment())
            mainActivity.addFragmentNoAnim(CommunityRegisterFragment())
        }
    }

    private fun clickChip(){
        val viewpager = binding.viewpagerFragmentCommunity
        binding.chipFragmentCommunityAll.setOnClickListener {
            viewpager.adapter = CommunityMypageAdapter(requireActivity(), tabPosition, 0)
        }

        binding.chipFragmentCommunityMyboard.setOnClickListener{
            viewpager.adapter = CommunityMypageAdapter(requireActivity(), tabPosition, 1)
        }

        binding.chipFragmentCommunityMycomment.setOnClickListener {
            viewpager.adapter = CommunityMypageAdapter(requireActivity(), tabPosition, 2)
        }
        binding.chipFragmentCommunityLike.setOnClickListener {
            viewpager.adapter = CommunityMypageAdapter(requireActivity(), tabPosition, 3)
        }
    }

    // tabLayout 설정
    private fun setTabLayout(){
        val tabTitleArr = arrayOf(
            R.string.fragment_community_tablayout_title_free
            , R.string.fragment_community_tablayout_title_drawing
        )
        val viewpager = binding.viewpagerFragmentCommunity
        tabLayout = binding.tablayoutFragmentCommunity

        viewpager.adapter = CommunityBoardAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewpager){ tab, position ->
            tab.text = resources.getString(tabTitleArr[position])
        }.attach()

        tabLayout.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.i(TAG, "onTabSelected: 클릭")
                tabPosition = tab!!.position
                when(tabPosition){
                    0 -> {
                        binding.fabFragmentCommunity.show()
                    }
                    1 -> {
                        binding.fabFragmentCommunity.hide()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}