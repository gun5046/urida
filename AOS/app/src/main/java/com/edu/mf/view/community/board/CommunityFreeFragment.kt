package com.edu.mf.view.community.board

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.databinding.FragmentCommunityBoardBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.repository.model.User
import com.edu.mf.repository.model.community.BoardListItem
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.CommunityFragment
import com.edu.mf.viewmodel.CommunityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "CommunityFreeFragment"
class CommunityFreeFragment(
    private val tabPosition: Int
    ): Fragment() {
    private lateinit var binding: FragmentCommunityBoardBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var communityService: CommunityService
    private lateinit var communityViewModel: CommunityViewModel
    private lateinit var user: User

    companion object{
        var myBoard = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBoardBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        communityService = mainActivity.communityService
        communityViewModel = CommunityViewModel()
        user = App.sharedPreferencesUtil.getUser()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chipClickListener()
    }

    // 자유게시판 recyclerview 설정
    private fun setFreeAdapter(boardList: List<BoardListItem>){
        val freeAdapter = CommunityFreeAdapter(mainActivity, communityViewModel, boardList)
        binding.recyclerviewFragmentCommunity.apply {
            adapter = freeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    // 그림게시판 recyclerview 설정
    private fun setDrawingAdapter(boardList: List<BoardListItem>){
        val drawingAdapter = CommunityDrawingAdapter(mainActivity, communityViewModel, boardList)
        binding.recyclerviewFragmentCommunity.apply {
            adapter = drawingAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    // chip 선택에 따른 화면 변화
    private fun chipClickListener(){
        myBoard = false
        when(CommunityFragment.chipPosition){
            0 -> getFreeBoardData()
            1 -> getMyBoardList()
            3 -> getLikeBoardList()
        }
    }

    // 게시판 리스트 받아오기
    private fun getFreeBoardData(){
        communityService.getBoardList(tabPosition)
            .enqueue(object : Callback<List<BoardListItem>> {
                override fun onResponse(
                    call: Call<List<BoardListItem>>,
                    response: Response<List<BoardListItem>>
                ) {
                    if (response.code() == 200){
                        val body = response.body()!!

                        if (tabPosition == 0){
                            setFreeAdapter(body)
                        } else{
                            setDrawingAdapter(body)
                        }
                    }
                }

                override fun onFailure(call: Call<List<BoardListItem>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 내가 쓴 게시글 목록 받기
    private fun getMyBoardList(){
        myBoard = true
        communityService.getMyBoardList(tabPosition, user.uid!!)
            .enqueue(object : Callback<List<BoardListItem>>{
                override fun onResponse(
                    call: Call<List<BoardListItem>>,
                    response: Response<List<BoardListItem>>
                ) {
                    if (response.code() == 200){
                        val body = response.body()!!

                        if (tabPosition == 0){
                            setFreeAdapter(body)
                        } else{
                            setDrawingAdapter(body)
                        }
                    }
                }

                override fun onFailure(call: Call<List<BoardListItem>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }

    // 좋아요 게시글 목록 받기
    private fun getLikeBoardList(){
        communityService.getLikeBoardList(tabPosition, user.uid!!)
            .enqueue(object : Callback<List<BoardListItem>>{
                override fun onResponse(
                    call: Call<List<BoardListItem>>,
                    response: Response<List<BoardListItem>>
                ) {
                    if (response.code() == 200){
                        val body = response.body()!!

                        if (tabPosition == 0){
                            setFreeAdapter(body)
                        } else{
                            setDrawingAdapter(body)
                        }
                    }
                }

                override fun onFailure(call: Call<List<BoardListItem>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }
}