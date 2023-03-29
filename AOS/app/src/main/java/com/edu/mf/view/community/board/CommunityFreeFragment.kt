package com.edu.mf.view.community.board

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.databinding.FragmentCommunityBoardBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.repository.model.community.BoardListItem
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.CommunityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "CommunityFreeFragment"
class CommunityFreeFragment: Fragment() {
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

    // 자유게시판 recyclerview 설정
    private fun setFreeAdapter(boardList: List<BoardListItem>){
        val freeAdapter = CommunityFreeAdapter(mainActivity, communityViewModel, boardList)
        binding.recyclerviewFragmentCommunity.apply {
            adapter = freeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    // 게시판 리스트 받아오기
    private fun getFreeBoardData(){
        communityService.getBoardList(0)
            .enqueue(object : Callback<List<BoardListItem>> {
                override fun onResponse(
                    call: Call<List<BoardListItem>>,
                    response: Response<List<BoardListItem>>
                ) {
                    if (response.code() == 200){
                        val body = response.body()!!
                        setFreeAdapter(body)
                    }
                }

                override fun onFailure(call: Call<List<BoardListItem>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }
}