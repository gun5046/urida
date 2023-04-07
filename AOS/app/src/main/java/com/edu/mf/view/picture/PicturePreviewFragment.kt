package com.edu.mf.view.picture

import android.media.AudioFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edu.mf.R
import com.edu.mf.databinding.FragmentPicturePreviewBinding
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.vision.demo.kotlin.objectdetector.ObjectDetectorProcessor
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions


class PicturePreviewFragment : Fragment() {
    private var cameraSource: CameraSource? = null
    private var graphicOverlay: GraphicOverlay? = null
    private var translator: Translator? = null
    private val localModel = LocalModel.Builder()
        .setAssetFilePath("object_labeler.tflite")
        .build()
    private val customObjectDetectorOptions =
        CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(CustomObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableClassification()
            .enableMultipleObjects().setMaxPerObjectLabelCount(1)
            .build()
    private lateinit var binding: FragmentPicturePreviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPicturePreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        graphicOverlay = binding.graphicOverlay
        if (cameraSource == null) {
            cameraSource = CameraSource(requireActivity(), graphicOverlay)
        }
        val translateOptions = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.KOREAN)
            .build()
        translator = Translation.getClient(translateOptions)
        translator!!.downloadModelIfNeeded()
        lifecycle.addObserver(translator!!)
        cameraSource!!.setMachineLearningFrameProcessor(
            ObjectDetectorProcessor(requireContext(), customObjectDetectorOptions, translator!!)
        )
        binding.cameraSourcePreview.start(cameraSource, graphicOverlay)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (cameraSource != null) {
            cameraSource?.release()
        }
    }
}