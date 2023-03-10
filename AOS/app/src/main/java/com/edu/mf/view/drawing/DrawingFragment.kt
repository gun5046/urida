package com.edu.mf.view.drawing

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.edu.mf.R
import com.edu.mf.databinding.FragmentDrawingBinding
import com.edu.mf.view.common.MainActivity
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DrawingFragment: Fragment() {
    private lateinit var binding: FragmentDrawingBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var toolList: ArrayList<ImageView>

    companion object{
        lateinit var graphicView:GraphicView
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
        graphicView = GraphicView(this.requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintlayoutCanvas.addView(graphicView)
        graphicView.paint.color = Color.BLACK
        graphicView.paint.strokeWidth = 10.0F

        initList()
        //extendLayout()
        penClickListener()
        penLongClickListener()

        binding.imageviewEraser.setOnClickListener {
            binding.imageviewEraser.y = -60F
            graphicView.undo()

            CoroutineScope(Dispatchers.Main).launch {
                delay(200)
                binding.imageviewEraser.y = -104F
            }
        }
    }

    // drawTools layout 크기 변경
    private fun extendLayout(){
        val drawToolsLayout = binding.constraintlayoutDrawTools
        val layoutParams = drawToolsLayout.layoutParams
        layoutParams.height = drawToolsLayout.resources.getDimensionPixelOffset(R.dimen.layout_size)
        binding.constraintlayoutDrawTools.layoutParams = layoutParams
    }

    // pen 클릭 이벤트
    private fun penClickListener(){
        binding.imageviewPalette.setOnClickListener {
            startActivity(Intent(context, ColorPickerActivity::class.java))
            penPullPut(0)
        }

        binding.imageviewPenRed.setOnClickListener {
            graphicView.paint.color = Color.RED
            penPullPut(1)
        }

        binding.imageviewPenYellow.setOnClickListener {
            graphicView.paint.color = Color.YELLOW
            penPullPut(2)
        }

        binding.imageviewPenGreen.setOnClickListener {
            graphicView.paint.color = Color.GREEN
            penPullPut(3)
        }

        binding.imageviewPenBlue.setOnClickListener {
            graphicView.paint.color = Color.BLUE
            penPullPut(4)
        }

        binding.imageviewPenBlack.setOnClickListener {
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
            imageviewPalette.setOnLongClickListener {
                cardviewPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
            imageviewPenRed.setOnLongClickListener {
                cardviewPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
            imageviewPenYellow.setOnLongClickListener {
                cardviewPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
            imageviewPenGreen.setOnLongClickListener {
                cardviewPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
            imageviewPenBlue.setOnLongClickListener {
                cardviewPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
            imageviewPenBlack.setOnLongClickListener {
                cardviewPenWidth.visibility = View.VISIBLE
                penWidthClickListener()
                return@setOnLongClickListener true
            }
        }
    }

    // pen shape 클릭 이벤트
    private fun penWidthClickListener(){
        binding.apply {
            imageviewPenWidth1.setOnClickListener {
                cardviewPenWidth.visibility = View.GONE
                setStrokeWidth(1)
            }
            imageviewPenWidth2.setOnClickListener {
                cardviewPenWidth.visibility = View.GONE
                setStrokeWidth(2)
            }
            imageviewPenWidth3.setOnClickListener {
                cardviewPenWidth.visibility = View.GONE
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
            binding.imageviewPalette,
            binding.imageviewPenRed,
            binding.imageviewPenYellow,
            binding.imageviewPenGreen,
            binding.imageviewPenBlue,
            binding.imageviewPenBlack
        )
    }
}