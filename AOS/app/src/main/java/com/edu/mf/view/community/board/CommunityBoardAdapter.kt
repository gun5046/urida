package com.edu.mf.view.community.board

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.edu.mf.databinding.FragmentCommunityBinding

private const val TAB_SIZE = 2
class CommunityBoardAdapter(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                CommunityFreeFragment()
            }
            else -> {
                CommunityDrawingFragment()
            }
        }
    }

    override fun getItemCount(): Int {
        return TAB_SIZE
    }
}