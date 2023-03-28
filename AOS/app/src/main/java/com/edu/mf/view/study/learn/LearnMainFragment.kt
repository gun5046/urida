package com.edu.mf.view.study.learn

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.FragmentLearnMainBinding
import com.edu.mf.utils.App
import com.edu.mf.utils.SharedPreferencesUtil
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import java.util.*


class LearnMainFragment : Fragment() {

    private var textToSpeech: TextToSpeech? = null
    private lateinit var binding : FragmentLearnMainBinding
    private lateinit var viewmodel : MainViewModel
    private lateinit var mainActivity: MainActivity
    private val TAG = "LearnMainFragment_지훈"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_learn_main,container,false)
        mainActivity = MainActivity.getInstance()!!
        viewmodel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding.apply {
            binding.list = App.PICTURES[viewmodel.selectedCategory]
            binding.vm = viewmodel
            handlers = this@LearnMainFragment
        }

        binding.lifecycleOwner = this
        disableBackPress()
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        viewmodel.currentIndex.observe(viewLifecycleOwner, Observer {
            viewmodel.changeBookMark("${it+1}/${App.PICTURES[viewmodel.selectedCategory].size}")
            viewmodel.setCurrentAnswer(App.PICTURES[viewmodel.selectedCategory][it])
        })
        viewmodel.setTTS()
    }

    private fun init(){
        getBookMark()
    }

    private fun getBookMark(){

        when(viewmodel.selectedCategory){
            0->viewmodel.setCurrentIndex(SharedPreferencesUtil(requireContext()).getFruitsBookMark())
            1->viewmodel.setCurrentIndex(SharedPreferencesUtil(requireContext()).getJobsBookMark())
            2->viewmodel.setCurrentIndex(SharedPreferencesUtil(requireContext()).getAnimalsBookMark())
            3->viewmodel.setCurrentIndex(SharedPreferencesUtil(requireContext()).getObjectsBookMark())
            4->viewmodel.setCurrentIndex(SharedPreferencesUtil(requireContext()).getPlacesBookMark())
            else->viewmodel.setCurrentIndex(SharedPreferencesUtil(requireContext()).getActionsBookMark())
        }
    }
    private fun setBookMark(){
        when(viewmodel.selectedCategory){
            0->SharedPreferencesUtil(requireContext()).setFruitsBookMark(viewmodel.currentIndex.value!!)
            1->SharedPreferencesUtil(requireContext()).setJobsBookMark(viewmodel.currentIndex.value!!)
            2->SharedPreferencesUtil(requireContext()).setAnimalsBookMark(viewmodel.currentIndex.value!!)
            3->SharedPreferencesUtil(requireContext()).setObjectsBookMark(viewmodel.currentIndex.value!!)
            4->SharedPreferencesUtil(requireContext()).setPlacesBookMark(viewmodel.currentIndex.value!!)
            else->SharedPreferencesUtil(requireContext()).setActionsBookMark(viewmodel.currentIndex.value!!)
        }
    }

    override fun onPause() {
        super.onPause()
        setBookMark()
    }
    fun backPressed(){
        mainActivity.popFragment()
    }

    /**
     * onBackPressed 막기
     */
    private fun disableBackPress(){
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        })
    }

}