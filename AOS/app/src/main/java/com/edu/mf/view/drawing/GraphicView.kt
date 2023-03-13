package com.edu.mf.view.drawing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GraphicView(context: Context): View(context){
    var paint = Paint()

    companion object{
        val pointList = ArrayList<Point>()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x.toInt()
        val y = event.y.toInt()

        if (x < 0 || y < 0){
            return false
        }

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                pointList.add(Point(x, y, false, paint.strokeWidth, paint.color))
            }
            MotionEvent.ACTION_MOVE -> {
                pointList.add(Point(x, y, true, paint.strokeWidth, paint.color))
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        performClick()

        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE

        val originalColor = paint.color
        for (i in 1 until pointList.size) {
            val prevPoint = pointList[i - 1]
            val nowPoint = pointList[i]

            if (nowPoint.move) {
                paint.color = nowPoint.color
                paint.strokeWidth = nowPoint.width

                canvas!!.drawLine(
                    prevPoint.x.toFloat(),
                    prevPoint.y.toFloat(),
                    nowPoint.x.toFloat(),
                    nowPoint.y.toFloat(),
                    paint
                )
            }
        }
        paint.color = originalColor
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
    var width: Float,
    var color: Int
)