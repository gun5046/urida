package com.edu.mf.view.community.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityDetailBinding
import com.edu.mf.view.common.MainActivity

class CommunityDetailFragment: Fragment() {
    private lateinit var binding: FragmentCommunityDetailBinding
    private lateinit var mainActivity: MainActivity

    private var clickState = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityDetailBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeLikeImgVisibility()
        binding.imageviewFragmentCommunityDetailLikeEmpty.setOnClickListener {
            changeLikeState()
        }
        binding.imageviewFragmentCommunityDetailLikeFull.setOnClickListener {
            changeLikeState()
        }

        setCommentAdapter()
    }

    // 좋아요 이미지 변경
    private fun changeLikeImgVisibility(){
        if (!clickState){
            binding.imageviewFragmentCommunityDetailLikeEmpty
                .visibility = View.VISIBLE
            binding.imageviewFragmentCommunityDetailLikeFull
                .visibility = View.GONE
        } else{
            binding.imageviewFragmentCommunityDetailLikeEmpty
                .visibility = View.GONE
            binding.imageviewFragmentCommunityDetailLikeFull
                .visibility = View.VISIBLE
        }
    }

    // 좋아요 클릭 상태 변경
    private fun changeLikeState(){
        if (!clickState){
            setAnim(true)
            clickState = true
            changeLikeImgVisibility()
        } else{
            setAnim(false)
            clickState = false
            changeLikeImgVisibility()
        }
    }

    // 좋아요 애니메이션 지정
    private fun setAnim(likeState: Boolean){
        if (!likeState){
            val emptyAnim = AnimationUtils.loadAnimation(
                requireContext().applicationContext
                , R.anim.community_detail_faidin_down_empty
            )
            binding.imageviewFragmentCommunityDetailLikeEmpty
                .startAnimation(emptyAnim)

            val fullAnim = AnimationUtils.loadAnimation(
                requireContext().applicationContext
                , R.anim.community_detail_fadeout_down_full
            )
            binding.imageviewFragmentCommunityDetailLikeFull
                .startAnimation(fullAnim)

        } else{
            val emptyAnim = AnimationUtils.loadAnimation(
                requireContext().applicationContext
                , R.anim.community_detail_fadeout_up_empty
            )
            binding.imageviewFragmentCommunityDetailLikeEmpty
                .startAnimation(emptyAnim)

            val fullAnim = AnimationUtils.loadAnimation(
                requireContext().applicationContext
                , R.anim.community_detail_fadein_up_full
            )
            binding.imageviewFragmentCommunityDetailLikeFull
                .startAnimation(fullAnim)
        }
    }

    // 댓글 recyclerview 설정
    private fun setCommentAdapter(){
        val commentAdapter = CommunityDetailCommentAdapter()
        binding.recyclerviewFragmentCommunityComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))
        }
    }
}