package com.edu.mf.view

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.edu.mf.R
import com.edu.mf.databinding.FragmentLanguageBinding
import com.edu.mf.databinding.FragmentMainBinding
import com.edu.mf.repository.model.Category
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.CommunityFragment
import com.edu.mf.view.drawing.DrawingFragment
import com.edu.mf.view.picture.PictureFragment
import com.edu.mf.view.study.StudyAdapter
import com.edu.mf.view.study.StudyFragment
import com.edu.mf.view.study.learn.LearnFragment
import com.edu.mf.view.study.quiz.QuizFragment
import com.edu.mf.view.study.reslove.ResolveFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment: Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var wordAdapter : StudyAdapter
    private var wordList : ArrayList<Category> = arrayListOf()
    private var photoList : ArrayList<Category> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        Glide.with(this).load(R.raw.charac).into(binding.imageviewChrac)

        //chkPermissionDrawingFragment()

        /*binding.apply {
            cardviewStudy.setOnClickListener {
                mainActivity.addFragment(StudyFragment())
            }

            cardviewPicture.setOnClickListener {
                mainActivity.addFragment(PictureFragment())
            }

            cardviewCommunity.setOnClickListener {
                mainActivity.addFragment(CommunityFragment())
            }

            imageviewSettings.setOnClickListener {
                showSettingDialog()
            }
        }*/
    }

    /*// 그림으로 찾기 클릭 시 permission 체크
    private fun chkPermissionDrawingFragment(){
        val reqPermission = requestPermission()
        binding.cardviewDrawing.setOnClickListener {
            reqPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }*/

    // 미디어 접근권한
    private fun requestPermission(): ActivityResultLauncher<String> {
        return registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it){
                mainActivity.addFragment(DrawingFragment())
            } else{
                Toast.makeText(
                    requireContext(), "저장장치 접근 권한을 확인해주세요", Toast.LENGTH_SHORT
                ).show()

                val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:" + requireContext().packageName))
                requireContext().startActivity(settingsIntent)
            }
        }
    }

    fun showSettingDialog(){
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = FragmentLanguageBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()
        dialogBinding.apply {
            layoutKorean.setOnClickListener {
                changeLocale(0)
                dialog.dismiss()
            }
            layoutChinese.setOnClickListener {
                changeLocale(1)
                dialog.dismiss()
            }
            layoutVietnam.setOnClickListener {
                changeLocale(2)
                dialog.dismiss()
            }
            layoutEnglish.setOnClickListener {
                changeLocale(3)
                dialog.dismiss()
            }
        }
        dialog.show()
    }
    private fun init(){
        setListData()
        setAdapter()
    }
    private fun setAdapter(){
        wordAdapter = StudyAdapter(wordList)
        binding.recylcerviewFragmentMainWord.apply{
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    /**
     * Recyclerview안에 들어가는 data 초기화
     */
    private fun setListData(){
        wordList.add(Category("낱말 익히기","평소 헷갈렸던 단어\n그림과 함께 \n학습해보아요",R.drawable.ic_learn,LearnFragment()))
        wordList.add(Category("낱말 퀴즈","같이 공부했던 단어를\n활용해서\n퀴즈를 풀어보아요",R.drawable.ic_quiz,QuizFragment()))
        wordList.add(Category("다시 풀어보기","아쉽게 틀렸던 \n퀴즈들은\n다시 한번 풀어보아요",R.drawable.ic_resolve,ResolveFragment()))
    }

    fun changeLocale(language: Int){
        mainActivity.changeLocale(language)
        CoroutineScope(Dispatchers.IO).launch {
            mainActivity.loginService.languageSetting(language, mainActivity.user!!.uid!!)
        }
        mainActivity.changeFragment(MainFragment())
    }
}