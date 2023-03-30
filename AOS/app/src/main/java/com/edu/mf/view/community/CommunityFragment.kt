package com.edu.mf.view.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.board.CommunityDrawingFragment
import com.edu.mf.view.community.board.CommunityFreeFragment
import com.edu.mf.view.community.chip.CommunityLikeFragment
import com.edu.mf.view.community.chip.CommunityMyBoardFragment
import com.edu.mf.view.community.chip.CommunityMyCommentFragment
import com.edu.mf.viewmodel.CommunityViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

private const val TAG = "CommunityFragment"
class CommunityFragment: Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var communityService: CommunityService
    private lateinit var communityViewModel: CommunityViewModel

    private var tabPosition = 0

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

        changeFrameLayout(CommunityFreeFragment())
        setTabLayout()
        clickChip()

        binding.fabFragmentCommunity.setOnClickListener{
            mainActivity.addFragmentNoAnim(CommunityRegisterFragment())
        }
    }

    private fun clickChip(){
        binding.chipFragmentCommunityAll.setOnClickListener {
            if (tabPosition == 0){
                changeFrameLayout(CommunityFreeFragment())
            } else{
                changeFrameLayout(CommunityDrawingFragment())
            }
        }

        binding.chipFragmentCommunityMyboard.setOnClickListener{
            changeFrameLayout(CommunityMyBoardFragment())
        }

        binding.chipFragmentCommunityMycomment.setOnClickListener {
            changeFrameLayout(CommunityMyCommentFragment())
        }
        binding.chipFragmentCommunityLike.setOnClickListener {
            changeFrameLayout(CommunityLikeFragment())
        }
    }

    // tabLayout 설정
    private fun setTabLayout(){
        val tabLayout = binding.tablayoutFragmentCommunity
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fragment_community_tablayout_title_free))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fragment_community_tablayout_title_drawing))

        tabLayout.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab!!.position
                when(tabPosition){
                    0 -> {
                        changeFrameLayout(CommunityFreeFragment())
                        binding.fabFragmentCommunity.show()
                    }
                    1 -> {
                        changeFrameLayout(CommunityDrawingFragment())
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

    // frameLayout 화면 전환
    private fun changeFrameLayout(fragment: Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout_fragment_community, fragment)
            .commit()
    }
}