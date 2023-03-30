package com.edu.mf.view.community.chip

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.edu.mf.view.community.CommunityFragment
import com.edu.mf.view.community.board.CommunityDrawingFragment
import com.edu.mf.view.community.board.CommunityFreeFragment

private const val TAB_SIZE = 4
class CommunityMypageAdapter(
    fragmentActivity: FragmentActivity,
    private val boardTabPosition: Int,
    private val chipPosition: Int
): FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        if (boardTabPosition == 0){  // 자유 게시판
            CommunityFragment.tabPosition = 0
            return when(chipPosition){
                0 -> CommunityFreeFragment()
                1 -> CommunityMyBoardFragment()
                2 -> CommunityMyCommentFragment()
                else -> CommunityLikeFragment()
            }
        } else{
            CommunityFragment.tabPosition = 1
            return when(chipPosition){  // 그림 게시판
                0 -> CommunityDrawingFragment()
                1 -> CommunityMyBoardFragment()
                2 -> CommunityMyCommentFragment()
                else -> CommunityLikeFragment()
            }
        }
    }

    override fun getItemCount(): Int {
        return TAB_SIZE
    }
}