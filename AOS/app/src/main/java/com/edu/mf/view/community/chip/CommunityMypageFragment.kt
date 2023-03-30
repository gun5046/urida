package com.edu.mf.view.community.chip

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.CommunityViewModel
import com.google.android.material.tabs.TabLayoutMediator

class CommunityMypageFragment: Fragment(), MenuProvider {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var communityService: CommunityService
    private lateinit var communityViewModel: CommunityViewModel

    private lateinit var actionBar: ActionBar

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

        setActionBar()
        setTabLayout()
        binding.fabFragmentCommunity.visibility = View.GONE
    }

    // 액션바 설정
    private fun setActionBar(){
        actionBar = mainActivity.supportActionBar!!
        actionBar.title = ""
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setBackgroundDrawable(
            ContextCompat.getDrawable(requireContext(), R.color.background_light_green)
        )
        actionBar.show()

        requireActivity().addMenuProvider(
            this
            , viewLifecycleOwner
            , Lifecycle.State.RESUMED
        )
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            android.R.id.home -> {
                mainActivity.popFragment()
                actionBar.hide()
            }
        }
        return true
    }

    // tabLayout 설정
    private fun setTabLayout(){
        val tabTitleArr = arrayOf(
            R.string.fragment_community_mypage_tablayout_title_board
            , R.string.fragment_community_mypage_tablayout_title_comment
        )
        val viewpager = binding.viewpagerFragmentCommunity
        val tabLayout = binding.tablayoutFragmentCommunity

        //viewpager.adapter = CommunityMypageAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewpager){ tab, position ->
            tab.text = resources.getString(tabTitleArr[position])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actionBar.hide()
    }
}