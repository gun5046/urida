package com.edu.mf.view.community

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.board.CommunityBoardFragment
import com.edu.mf.viewmodel.CommunityViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

private const val TAG = "CommunityFragment"
class CommunityFragment: Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var communityService: CommunityService
    private lateinit var communityViewModel: CommunityViewModel

    private var tabPosition = 0

    companion object{
        var chipPosition = 0
        var clickChip = false
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

        if (tabPosition != 0){
            binding.fabFragmentCommunity.hide()
        }
        clickChip = false
        changeFrameLayout(CommunityBoardFragment(tabPosition))

        setTabLayout()
        changeChipColorClick()
        chipClickListener()

        binding.fabFragmentCommunity.setOnClickListener{
            mainActivity.addFragmentNoAnim(
                CommunityRegisterFragment(null, 0)
            )
        }
    }

    // 클릭 상태에 따라 chip 색상 변화
    private fun changeChipColor(chip: Chip, chipClickState:Boolean){
        if (!chipClickState){
            chip.apply {
                chipBackgroundColor = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.grey_09)
                )
                setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.grey_00)
                )
            }
        } else{
            chip.apply {
                chipBackgroundColor = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.background_light_green)
                )
                setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
            }
        }
    }

    // chip 클릭 시 색상 변화
    private fun changeChipColorClick(){
        when(chipPosition){
            0 -> {
                changeChipColor(binding.chipFragmentCommunityAll, true)
                changeChipColor(binding.chipFragmentCommunityMyboard, false)
                changeChipColor(binding.chipFragmentCommunityMycomment, false)
                changeChipColor(binding.chipFragmentCommunityLike, false)
            }
            1 -> {
                changeChipColor(binding.chipFragmentCommunityAll, false)
                changeChipColor(binding.chipFragmentCommunityMyboard, true)
                changeChipColor(binding.chipFragmentCommunityMycomment, false)
                changeChipColor(binding.chipFragmentCommunityLike, false)
            }
            2 -> {
                changeChipColor(binding.chipFragmentCommunityAll, false)
                changeChipColor(binding.chipFragmentCommunityMyboard, false)
                changeChipColor(binding.chipFragmentCommunityMycomment, true)
                changeChipColor(binding.chipFragmentCommunityLike, false)
            }
            3 -> {
                changeChipColor(binding.chipFragmentCommunityAll, false)
                changeChipColor(binding.chipFragmentCommunityMyboard, false)
                changeChipColor(binding.chipFragmentCommunityMycomment, false)
                changeChipColor(binding.chipFragmentCommunityLike, true)
            }
        }
    }

    // chip 선택시 화면 변화시키기
    private fun chipClickListener(){
        binding.chipFragmentCommunityAll.setOnClickListener {
            clickChip = true
            chipPosition = 0
            changeChipColorClick()
            changeFrameLayout(CommunityBoardFragment(tabPosition))
        }

        binding.chipFragmentCommunityMyboard.setOnClickListener{
            clickChip = true
            chipPosition = 1
            changeChipColorClick()
            changeFrameLayout(CommunityBoardFragment(tabPosition))
        }

        binding.chipFragmentCommunityMycomment.setOnClickListener {
            clickChip = true
            chipPosition = 2
            changeChipColorClick()
            changeFrameLayout(CommunityBoardFragment(tabPosition))
        }

        binding.chipFragmentCommunityLike.setOnClickListener {
            clickChip = true
            chipPosition = 3
            changeChipColorClick()
            changeFrameLayout(CommunityBoardFragment(tabPosition))
        }
    }

    // tabLayout 설정
    private fun setTabLayout(){
        val tabLayout = binding.tablayoutFragmentCommunity
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fragment_community_tablayout_title_free))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fragment_community_tablayout_title_drawing))
        tabLayout.getTabAt(tabPosition)!!.select()

        tabLayout.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab!!.position
                CommunityBoardFragment.rViewItemPosition = 0
                changeFrameLayout(CommunityBoardFragment(tabPosition))
                when(tabPosition){
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

    // frameLayout 화면 전환
    private fun changeFrameLayout(fragment: Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout_fragment_community, fragment)
            .commit()
    }
}