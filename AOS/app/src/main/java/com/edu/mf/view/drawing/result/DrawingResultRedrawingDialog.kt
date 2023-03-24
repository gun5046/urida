package com.edu.mf.view.drawing.result

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.edu.mf.databinding.DialogFragmentDrawingResultShareBinding
import com.edu.mf.view.MainFragment
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.CommunityFragment
import com.edu.mf.view.community.CommunityRegisterFragment
import com.edu.mf.view.drawing.DrawingFragment

class DrawingResultShareDialog: DialogFragment() {
    private lateinit var binding: DialogFragmentDrawingResultShareBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentDrawingResultShareBinding.inflate(layoutInflater)
        mainActivity = MainActivity.getInstance()!!

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        isCancelable = false

        binding.buttonDialogFragmentDrawingResultShareMain.setOnClickListener {
            mainActivity.popFragment()
        }

        binding.buttonDialogFragmentDrawingResultShare.setOnClickListener {
            dialog?.dismiss()
            mainActivity.popFragment()
            mainActivity.addFragment(CommunityFragment())
            mainActivity.addFragmentNoAnim(CommunityRegisterFragment())
        }

        return binding.root
    }
}