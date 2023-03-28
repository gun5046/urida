package com.edu.mf.view.drawing.result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.edu.mf.databinding.ItemFragmentDrawingResultViewpagerBinding
import com.edu.mf.repository.model.drawing.DrawingResponse
import com.edu.mf.viewmodel.DrawingViewModel
import com.edu.mf.viewmodel.MainViewModel

class DrawingResultViewPagerItemFragment(
    private val context:Context
    , private val drawingViewModel: DrawingViewModel
    , private val resultWordList:ArrayList<String>
    , private val drawingResponse: DrawingResponse
    ): PagerAdapter() {
    private lateinit var binding: ItemFragmentDrawingResultViewpagerBinding
    private lateinit var mainViewModel: MainViewModel

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        binding = ItemFragmentDrawingResultViewpagerBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            , container
            , false
        )

        mainViewModel = MainViewModel()
        DrawingResultFragment(drawingResponse)
            .setMainViewModel(position, mainViewModel, drawingViewModel)
        binding.mainViewModel = mainViewModel

        val categoryIdx = drawingViewModel.imgInfoList.value?.get(position)!!.categoryIdx
        val pictureIdx = drawingViewModel.imgInfoList.value?.get(position)!!.pictureIdx
        binding.categoryIdx = categoryIdx
        binding.pictureIdx = pictureIdx
        binding.drawingResultWord = resultWordList[position]

        container.addView(binding.root)

        return binding.root
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun getCount(): Int {
        return 2
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}