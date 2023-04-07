package com.edu.mf.view.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.edu.mf.databinding.DialogNotificationBinding

class NotificationDialog(
    private val notiText: String
): DialogFragment() {
    private lateinit var binding: DialogNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNotificationBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding.message = notiText
        binding.buttonDialogNoti.setOnClickListener {
            this.dismiss()
        }

        return binding.root
    }
}