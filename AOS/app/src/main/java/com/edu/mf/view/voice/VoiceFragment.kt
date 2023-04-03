package com.edu.mf.view.voice

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.DialogVoiceWaitBinding
import com.edu.mf.databinding.FragmentVoiceBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.VoiceViewModel
import com.github.squti.androidwaverecorder.WaveRecorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.File

//http://j8d202.p.ssafy.io:8084/stt/audiopredict/ POST, PCM 파일 보내서 단어 받기
private const val TAG = "VoiceFragment"

class VoiceFragment : Fragment() {
    private lateinit var binding: FragmentVoiceBinding
    private var filePath: String? = null
    private lateinit var waveRecorder: WaveRecorder
    private var isRecording = false
    private lateinit var mainActivity: MainActivity
    private lateinit var voiceViewModel: VoiceViewModel
    private var dialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = MainActivity.getInstance()!!
        voiceViewModel = ViewModelProvider(requireActivity())[VoiceViewModel::class.java]
        binding = FragmentVoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filePath = File(requireContext().getExternalFilesDir(null), "/record.wav").absolutePath
        waveRecorder = WaveRecorder(filePath!!)
        Log.d(TAG, "onViewCreated: ${requireContext().getExternalFilesDir(null)}")
        val recordPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            if(it[Manifest.permission.RECORD_AUDIO] == true && it[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true && it[Manifest.permission.READ_EXTERNAL_STORAGE] == true){
                onRecordButtonClick()
            } else {
                Toast.makeText(requireContext(), requireContext().getString(R.string.fragment_voice_permission_record_audio), Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonRecord.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                onRecordButtonClick()
            } else {
                recordPermissionLauncher.launch(arrayListOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray())
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun onRecordButtonClick(){
        if(isRecording){
            isRecording = false
            waveRecorder.stopRecording()
            showDialog()
            CoroutineScope(Dispatchers.IO).launch {
                val body = File(filePath!!).asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val response = mainActivity.sttService.sttResult(MultipartBody.Part.createFormData("audio_file", "record.wav", body))
                if(response.code() == 200){
                    voiceViewModel.setWord(response.body()!!)
                }
                mainActivity.runOnUiThread {
                    dialog!!.dismiss()
                    mainActivity.addFragment(VoiceResultFragment())
                }
            }
//            binding.buttonRecord.text = requireContext().resources.getString(R.string.fragment_voice_button_record)
        } else {
            isRecording = true
            waveRecorder.startRecording()
            binding.textVoice.text = requireContext().getString(R.string.fragment_voice_text_voice_recording)
            binding.buttonRecord.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_stop))
        }
    }

    fun showDialog(){
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = DialogVoiceWaitBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog!!.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.show()
    }
}