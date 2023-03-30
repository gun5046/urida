package com.edu.mf.view.community.board

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.edu.mf.databinding.FragmentCommunityBoardBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.repository.model.community.BoardListItem
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.CommunityFragment
import com.edu.mf.viewmodel.CommunityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "CommunityDrawingFragmen"
class CommunityDrawingFragment: Fragment() {
    private lateinit var binding: FragmentCommunityBoardBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var communityService: CommunityService
    private lateinit var communityViewModel: CommunityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBoardBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        communityService = mainActivity.communityService
        communityViewModel = CommunityViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFreeBoardData()
    }

    // 그림게시판 recyclerview 설정
    private fun setDrawingAdapter(boardList: List<BoardListItem>){
        val drawingAdapter = CommunityDrawingAdapter(mainActivity, communityViewModel, boardList)
        binding.recyclerviewFragmentCommunity.apply {
            adapter = drawingAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    // 게시판 리스트 받아오기
    private fun getFreeBoardData(){
        communityService.getBoardList(1)
            .enqueue(object : Callback<List<BoardListItem>> {
                override fun onResponse(
                    call: Call<List<BoardListItem>>,
                    response: Response<List<BoardListItem>>
                ) {
                    if (response.code() == 200){
                        val body = response.body()!!
                        setDrawingAdapter(body)
                    }
                }

                override fun onFailure(call: Call<List<BoardListItem>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }
}