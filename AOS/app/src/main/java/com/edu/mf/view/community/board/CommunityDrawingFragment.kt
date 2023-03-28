package com.edu.mf.view.community.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.edu.mf.databinding.FragmentCommunityBoardBinding
import com.edu.mf.view.common.MainActivity

class CommunityDrawingFragment: Fragment() {
    private lateinit var binding: FragmentCommunityBoardBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBoardBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDrawingAdapter()
    }

    // 그림게시판 recyclerview 설정
    private fun setDrawingAdapter(){
        val drawingAdapter = CommunityDrawingAdapter(mainActivity)
        binding.recyclerviewFragmentCommunity.apply {
            adapter = drawingAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}