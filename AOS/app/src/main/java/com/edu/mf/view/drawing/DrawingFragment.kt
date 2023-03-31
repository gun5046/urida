package com.edu.mf.view.drawing

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.edu.mf.R
import com.edu.mf.databinding.FragmentDrawingBinding
import com.edu.mf.repository.model.drawing.DrawingRequest
import com.edu.mf.repository.model.drawing.DrawingResponse
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.drawing.result.DrawingResultFragment
import com.edu.mf.view.drawing.result.DrawingResultShareDialog
import com.edu.mf.view.drawing.result.DrawingResultViewPagerFragment
import com.edu.mf.viewmodel.DrawingViewModel
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "DrawingFragment"
class DrawingFragment: Fragment() {
    private lateinit var binding: FragmentDrawingBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var drawingViewModel:DrawingViewModel

    private lateinit var pointList: ArrayList<Point>
    private lateinit var toolList: ArrayList<ImageView>
    private lateinit var matrix: MutableList<ArrayList<ArrayList<Int>>>

    companion object{
        lateinit var graphicView:GraphicView
        lateinit var drawingBitmap: Bitmap
        val colorPicker = ColorPickerDialog.newBuilder()!!
        var palette = mutableSetOf<Int>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        drawingViewModel = DrawingViewModel()
        graphicView = GraphicView(this.requireContext(), binding.constraintlayoutFragmentDrawingCanvas)
        pointList = GraphicView.pointList

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintlayoutFragmentDrawingCanvas.addView(graphicView)
        graphicView.paint.color = Color.BLACK
        graphicView.paint.strokeWidth = 10.0F

        initList()
        clickBackPress()
        penClickListener()
        penLongClickListener()

        binding.imageviewFragmentDrawingEraser.setOnClickListener {
            binding.imageviewFragmentDrawingEraser.y = -60F
            graphicView.undo()

            CoroutineScope(Dispatchers.Main).launch {
                delay(100)
                binding.imageviewFragmentDrawingEraser.y = -104F
            }
        }

        binding.buttonFragmentDrawingDrawingResult.setOnClickListener {
            makeMatrix()

            if (matrix.size != 0) {
                sendMatrix()
            }
        }
    }

    // response 결과에 따라 이동할 Fragment 결정
    private fun changeFragment(){
        drawingViewModel.drawingResponse.observe(viewLifecycleOwner, Observer{
            when(it.predictionType){
                0 -> mainActivity.addFragment(DrawingResultFragment(it))
                1 -> mainActivity.addFragment(DrawingResultViewPagerFragment(it))
                2 -> {
                    val dialog = DrawingResultShareDialog()
                    dialog.show(childFragmentManager, "DrawingResultDialog")
                }
            }
        })
    }

    // 3차원 행렬 만들기
    private fun makeMatrix(){
        var xList = ArrayList<Int>()
        var yList = ArrayList<Int>()

        matrix = mutableListOf()
        for (i in 1 until pointList.size){
            if (!pointList[i].move){
                if (xList.size != 0 && yList.size != 0){
                    matrix.add(arrayListOf(xList, yList))
                    xList = ArrayList()
                    yList = ArrayList()
                }
            }
            xList.add(pointList[i].x)
            yList.add(pointList[i].y)
        }

        if (xList.size != 0){
            matrix.add(arrayListOf(xList, yList))
        }
        pointList.clear()
    }

    // 3차원 행렬 서버로 전송
    private fun sendMatrix(){
        mainActivity.drawingService.drawingResult(
            DrawingRequest(
                matrix.toCollection(ArrayList())
                , binding.constraintlayoutFragmentDrawingCanvas.width
                , binding.constraintlayoutFragmentDrawingCanvas.height
            )
        ).enqueue(object : Callback<DrawingResponse>{
            override fun onResponse(
                call: Call<DrawingResponse>,
                response: Response<DrawingResponse>
            ) {
                if (response.code() == 200){
                    val body = response.body()!!
                    drawingViewModel.setDrawingResponse(body)

                    changeFragment()
                }
            }

            override fun onFailure(call: Call<DrawingResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // pen 클릭 이벤트
    private fun penClickListener(){
        binding.imageviewFragmentDrawingPalette.setOnClickListener {
            startActivity(Intent(context, ColorPickerActivity::class.java))
            penPullPut(0)
        }

        binding.imageviewFragmentDrawingPenRed.setOnClickListener {
            graphicView.paint.color = Color.RED
            penPullPut(1)
        }

        binding.imageviewFragmentDrawingPenYellow.setOnClickListener {
            graphicView.paint.color = Color.YELLOW
            penPullPut(2)
        }

        binding.imageviewFragmentDrawingPenGreen.setOnClickListener {
            graphicView.paint.color = ContextCompat.getColor(
                requireContext(), R.color.primary_teal_200
            )
            penPullPut(3)
        }

        binding.imageviewFragmentDrawingPenBlue.setOnClickListener {
            graphicView.paint.color = Color.BLUE
            penPullPut(4)
        }

        binding.imageviewFragmentDrawingPenBlack.setOnClickListener {
            graphicView.paint.color = Color.BLACK
            penPullPut(5)
        }
    }

    // 사용중인 펜 꺼내고 넣기
    private fun penPullPut(pullIdx: Int){
        for (i in 0 until toolList.size){
            if (i == pullIdx){
                toolList[i].y = 0F
                continue
            }
            toolList[i].y = -52F
        }
    }

    // pen 롱 클릭 이벤트
    private fun penLongClickListener(){
        binding.apply {
            imageviewFragmentDrawingPalette.setOnLongClickListener {
                cardviewFragmentDrawingPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
            imageviewFragmentDrawingPenRed.setOnLongClickListener {
                cardviewFragmentDrawingPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
            imageviewFragmentDrawingPenYellow.setOnLongClickListener {
                cardviewFragmentDrawingPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
            imageviewFragmentDrawingPenGreen.setOnLongClickListener {
                cardviewFragmentDrawingPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
            imageviewFragmentDrawingPenBlue.setOnLongClickListener {
                cardviewFragmentDrawingPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
            imageviewFragmentDrawingPenBlack.setOnLongClickListener {
                cardviewFragmentDrawingPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
        }
    }

    // pen shape 클릭 이벤트
    private fun penWidthClickListener(){
        binding.apply {
            imageviewFragmentDrawingPenWidth1.setOnClickListener {
                cardviewFragmentDrawingPenWidth.visibility = View.GONE
                setStrokeWidth(1)
            }
            imageviewFragmentDrawingPenWidth2.setOnClickListener {
                cardviewFragmentDrawingPenWidth.visibility = View.GONE
                setStrokeWidth(2)
            }
            imageviewFragmentDrawingPenWidth3.setOnClickListener {
                cardviewFragmentDrawingPenWidth.visibility = View.GONE
                setStrokeWidth(3)
            }
        }
    }

    // strokeWidth 변경
    private fun setStrokeWidth(width: Int){
        when(width){
            1 -> graphicView.paint.strokeWidth = 10F
            2 -> graphicView.paint.strokeWidth = 20F
            3 -> graphicView.paint.strokeWidth = 30F
        }
    }

    // List 초기화
    private fun initList(){
        // colorPicker palette 색상 초기화
        val colorList = resources.getIntArray(R.array.drawing_palette)
        for (color in colorList){
            palette.add(color)
        }

        // toolList 초기화
        toolList = arrayListOf(
            binding.imageviewFragmentDrawingPalette,
            binding.imageviewFragmentDrawingPenRed,
            binding.imageviewFragmentDrawingPenYellow,
            binding.imageviewFragmentDrawingPenGreen,
            binding.imageviewFragmentDrawingPenBlue,
            binding.imageviewFragmentDrawingPenBlack
        )
    }

    // onBackPressed시 현재 fragment pop
    private fun clickBackPress(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    mainActivity.popFragment()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pointList.clear()
    }
}