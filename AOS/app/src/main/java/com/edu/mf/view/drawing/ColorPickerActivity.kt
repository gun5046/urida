package com.edu.mf.view.drawing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edu.mf.R
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class ColorPickerActivity: AppCompatActivity(), ColorPickerDialogListener {
    private val colorPicker = DrawingFragment.colorPicker
    private val palette = DrawingFragment.palette

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        colorPicker
            .setDialogTitle(R.string.fragment_drawing_dialog_title)
            .setCustomButtonText(R.string.fragment_drawing_dialog_custom)
            .setPresetsButtonText(R.string.fragment_drawing_dialog_presets)
            .setSelectedButtonText(R.string.fragment_drawing_dialog_complete)
            .setPresets(palette.toIntArray())
            .show(this)
    }

    // 완료 버튼 클릭 시 팔레트에 색상 추가
    override fun onColorSelected(dialogId: Int, color: Int) {
        DrawingFragment.graphicView.paint.color = color
        palette.add(color)
        colorPicker.setPresets(palette.toIntArray())
        finish()
    }

    // 확인 버튼 안누르고 다이얼로그 종료할 때
    override fun onDialogDismissed(dialogId: Int) {
        finish()
    }
}