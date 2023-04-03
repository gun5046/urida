package com.edu.mf.view.drawing.result

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.edu.mf.databinding.DialogFragmentDrawingResultShareBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.CommunityFragment
import com.edu.mf.view.community.CommunityRegisterFragment
import com.edu.mf.view.drawing.DrawingFragment
import java.io.FileOutputStream

class DrawingResultShareDialog: DialogFragment() {
    private lateinit var binding: DialogFragmentDrawingResultShareBinding
    private lateinit var mainActivity: MainActivity

    companion object{
        var savedDrawingUri: Uri = "".toUri()
    }

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
            savedDrawingUri = saveFile(DrawingFragment.drawingBitmap)!!

            dialog?.dismiss()
            mainActivity.popFragment()
            mainActivity.addFragment(CommunityFragment())
            mainActivity.addFragmentNoAnim(
                CommunityRegisterFragment(
                    null, 1
                )
            )
        }

        return binding.root
    }

    // 파일 저장
    private fun saveFile(bitmap: Bitmap): Uri?{
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "mf_drawing")
        //values.put(Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            values.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null){
            val descriptor = requireContext().contentResolver.openFileDescriptor(uri, "w")

            if (descriptor != null){
                val fos = FileOutputStream(descriptor.fileDescriptor)
                //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.close()
                descriptor.close()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    requireContext().contentResolver.update(uri, values, null, null)
                }
            }
        }
        return uri
    }
}