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
import com.edu.mf.view.community.board.CommunityFreeFragment
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

    companion object{
        var chipPosition = 0
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
        changeFrameLayout(CommunityFreeFragment(tabPosition))
        setTabLayout()
        chipClickListener()

        binding.fabFragmentCommunity.setOnClickListener{
            mainActivity.addFragmentNoAnim(
                CommunityRegisterFragment(null, 0)
            )
        }
    }

    // chip 선택시 화면 변화시키기
    private fun chipClickListener(){
        binding.chipFragmentCommunityAll.setOnClickListener {
            chipPosition = 0
            changeFrameLayout(CommunityFreeFragment(tabPosition))
        }

        binding.chipFragmentCommunityMyboard.setOnClickListener{
            chipPosition = 1
            changeFrameLayout(CommunityFreeFragment(tabPosition))
        }

        binding.chipFragmentCommunityMycomment.setOnClickListener {
            chipPosition = 2
            changeFrameLayout(CommunityFreeFragment(tabPosition))
        }

        binding.chipFragmentCommunityLike.setOnClickListener {
            chipPosition = 3
            changeFrameLayout(CommunityFreeFragment(tabPosition))
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
                changeFrameLayout(CommunityFreeFragment(tabPosition))
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