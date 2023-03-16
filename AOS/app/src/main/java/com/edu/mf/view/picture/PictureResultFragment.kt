package com.edu.mf.view.picture

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.databinding.FragmentPictureResultBinding
import com.edu.mf.utils.BitmapUtil
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.PictureViewModel
import java.io.IOException

private const val TAG = "PictureResultFragment"

class PictureResultFragment : Fragment() {
    private lateinit var binding: FragmentPictureResultBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var pictureViewModel: PictureViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPictureResultBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        pictureViewModel = ViewModelProvider(requireActivity())[PictureViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bitmap = BitmapUtil.getBitmapFromContentUri(requireActivity().contentResolver, pictureViewModel.uri!!)
        binding.imageView.setImageBitmap(bitmap)
    }
}