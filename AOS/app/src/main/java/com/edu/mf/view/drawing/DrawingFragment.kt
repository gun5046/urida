package com.edu.mf.view.drawing

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.flatten
import androidx.fragment.app.Fragment
import com.edu.mf.databinding.FragmentDrawingBinding

class DrawingFragment: Fragment() {
    private lateinit var binding: FragmentDrawingBinding
    private lateinit var graphicView: GraphicView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        graphicView = GraphicView(view.context)
        binding.constraintlayoutCanvas.addView(graphicView)
        graphicView.paint.color = Color.BLUE

        binding.imageviewEraser.setOnClickListener {
            graphicView.undo()
        }

        binding.imageview1.setOnClickListener {
            graphicView.paint.color = Color.RED
        }

        binding.imageview2.setOnClickListener {
            graphicView.paint.color = Color.BLUE
        }
    }

    private class GraphicView(context: Context): View(context){
        private val pointList = ArrayList<Point>()
        private val path = Path()
        var paint = Paint()

        private var startX = -1
        private var startY = -1
        private var endX = -1
        private var endY = -1

        override fun onTouchEvent(event: MotionEvent?): Boolean {
            val x = event!!.x.toInt()
            val y = event.y.toInt()

            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    startX = x
                    startY = y
                    path.moveTo(x.toFloat(), y.toFloat())
                    pointList.add(Point(x, y, false, paint.color))
                }
                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(x.toFloat(), y.toFloat())
                    pointList.add(Point(x, y, true, paint.color))
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    endX = x
                    endY = y
                }
            }
            performClick()

            return true
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            paint.isAntiAlias = true
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 5.0F

            for (i in 1 until pointList.size) {
                val prevPoint = pointList[i - 1]
                val nowPoint = pointList[i]

                if (nowPoint.move) {
                    paint.color = prevPoint.color

                    canvas!!.drawLine(
                        prevPoint.x.toFloat(),
                        prevPoint.y.toFloat(),
                        nowPoint.x.toFloat(),
                        nowPoint.y.toFloat(),
                        paint
                    )
                }
            }
        }

        fun undo(){
            for (i in pointList.size-1 downTo 1){
                if (!pointList[i].move){
                    pointList.removeAt(i)
                    break
                }
                pointList.removeAt(i)
            }
            invalidate()
        }

        override fun performClick(): Boolean {
            return super.performClick()
        }
    }

    data class Point(
        var x: Int,
        var y: Int,
        var move: Boolean,
        var color: Int
    )
}