package com.edu.mf.view.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.edu.mf.databinding.DialogExitBinding

class ExitDialog: DialogFragment() {
    private lateinit var binding: DialogExitBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogExitBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding.buttonDialogExitCancel.setOnClickListener {
            this.dismiss()
        }

        binding.buttonDialogExitOut.setOnClickListener {
            mainActivity.finish()
        }

        return binding.root
    }
}