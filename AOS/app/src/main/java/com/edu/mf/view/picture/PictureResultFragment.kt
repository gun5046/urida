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
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.databinding.FragmentPictureResultBinding
import com.edu.mf.utils.BitmapUtil
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel
import com.edu.mf.viewmodel.PictureViewModel
import java.io.IOException

private const val TAG = "PictureResultFragment"

class PictureResultFragment : Fragment() {
    private lateinit var binding: FragmentPictureResultBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var pictureViewModel: PictureViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pictureResultAdapter: PictureResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPictureResultBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        pictureViewModel = ViewModelProvider(requireActivity())[PictureViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.setTTS()
        pictureResultAdapter = PictureResultAdapter(pictureViewModel.detectedPictureList.value!!, mainViewModel)
        Log.d(TAG, "onViewCreated: ${pictureViewModel.detectedPictureList.value!!.size}")
        if(pictureViewModel.detectedPictureList.value!!.size == 0){
            binding.recyclerview.visibility = View.GONE
            binding.layoutEmpty.visibility = View.VISIBLE
        } else {
            binding.recyclerview.visibility = View.VISIBLE
            binding.layoutEmpty.visibility = View.GONE
        }
        binding.recyclerview.adapter = pictureResultAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        pictureViewModel.detectedPictureList.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: observed ${it.size}")
            if(it.size == 0){
                binding.recyclerview.visibility = View.GONE
                binding.layoutEmpty.visibility = View.VISIBLE
            } else {
                binding.textviewFragmentPictureResultTitle.visibility = View.VISIBLE
                binding.textviewFragmentPictureResultSubtitle.visibility = View.VISIBLE
                binding.recyclerview.visibility = View.VISIBLE
                binding.layoutEmpty.visibility = View.GONE
            }
            pictureResultAdapter.PictureList = it
            pictureResultAdapter.notifyDataSetChanged()
        }
        binding.buttonEmpty.setOnClickListener {
            mainActivity.popFragment()
        }
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.stopTTS()
    }
}