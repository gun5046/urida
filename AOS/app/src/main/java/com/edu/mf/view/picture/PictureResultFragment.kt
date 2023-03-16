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
        val bitmap = getBitmapFromContentUri(requireActivity().contentResolver, pictureViewModel.uri!!)
        binding.imageView.setImageBitmap(bitmap)
    }

    fun getExifOrientationTag(resolver: ContentResolver, uri: Uri): Int {
        if (ContentResolver.SCHEME_CONTENT != uri.getScheme()
            && ContentResolver.SCHEME_FILE != uri.getScheme()
        ) {
            return 0
        }

        var exif: ExifInterface
        try {
            resolver.openInputStream(uri).use { inputStream ->
                if (inputStream == null) {
                    return 0
                }
                exif = ExifInterface(inputStream)
            }
        } catch (e: IOException) {
            Log.e(
                TAG,
                "failed to open file to read rotation meta data: $uri", e
            )
            return 0
        }

        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    }

    @Throws(IOException::class)
    fun getBitmapFromContentUri(contentResolver: ContentResolver, imageUri: Uri): Bitmap? {
        val decodedBitmap =
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri) ?: return null
        val orientation: Int = getExifOrientationTag(
            contentResolver,
            imageUri
        )
        var rotationDegrees = 0
        var flipX = false
        var flipY = false
        when (orientation) {
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flipX = true
            ExifInterface.ORIENTATION_ROTATE_90 -> rotationDegrees = 90
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                rotationDegrees = 90
                flipX = true
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> rotationDegrees = 180
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> flipY = true
            ExifInterface.ORIENTATION_ROTATE_270 -> rotationDegrees = -90
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                rotationDegrees = -90
                flipX = true
            }
            ExifInterface.ORIENTATION_UNDEFINED, ExifInterface.ORIENTATION_NORMAL -> {}
            else -> {}
        }
        return rotateBitmap(
            decodedBitmap,
            rotationDegrees,
            flipX,
            flipY
        )
    }

    private fun rotateBitmap(
        bitmap: Bitmap, rotationDegrees: Int, flipX: Boolean, flipY: Boolean
    ): Bitmap? {
        val matrix = Matrix()

        // Rotate the image back to straight.
        matrix.postRotate(rotationDegrees.toFloat())

        // Mirror the image along the X or Y axis.
        matrix.postScale(if (flipX) -1.0f else 1.0f, if (flipY) -1.0f else 1.0f)
        val rotatedBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        // Recycle the old bitmap if it has changed.
        if (rotatedBitmap != bitmap) {
            bitmap.recycle()
        }
        return rotatedBitmap
    }
}