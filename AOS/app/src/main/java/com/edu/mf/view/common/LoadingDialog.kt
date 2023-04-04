package com.edu.mf.view.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.edu.mf.R
import com.edu.mf.databinding.DialogLoadingBinding

class LoadingDialog: DialogFragment() {
    private lateinit var binding: DialogLoadingBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogLoadingBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireContext(), R.style.TransparentDialog)
        builder.setView(binding.root)
        isCancelable = false

        Glide.with(requireContext()).load(R.raw.loading).into(binding.imageviewDialogLoading)

        return builder.create()
    }
}