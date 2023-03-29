package com.edu.mf.view.study.quiz

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.DialogFragmentQuizResultBinding
import com.edu.mf.repository.api.ResolveService
import com.edu.mf.repository.model.resolve.ResolveRequest
import com.edu.mf.repository.model.resolve.ResolveResponse
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "QuizResultDialog_지훈"
class QuizResultDialog(
    answers : Int,
    flag : Int
) : DialogFragment(){
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: MainViewModel
    private var _binding: DialogFragmentQuizResultBinding? = null
    private val binding get() = _binding!!
    private var answers : Int?= null
    private var job: Job? = null
    private var flag : Int? = null
    init{
        this.answers = answers
        this.flag = flag
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate<DialogFragmentQuizResultBinding>(inflater, R.layout.dialog_fragment_quiz_result,container,false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!

        if(viewModel.resolveMode)checkResolveAnswer()
        else checkAnswer()

        binding.apply {
            handlers = this@QuizResultDialog
            lifecycleOwner = this@QuizResultDialog
            vm = viewModel
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        return view
    }
    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this@QuizResultDialog,0.9f,0.15f)
    }


    private fun checkResolveAnswer(){
        val current_resolve = viewModel.resolve.value!![viewModel.resolveIndex.value!!]
        when(flag){
            0->{
                if(answers==viewModel.answerIndex){
                    binding.textviewDialogFragmentQuizTitle.text = "정답입니다"
                    deleteResolve(current_resolve.pro_id)
                }
                else{
                    binding.textviewDialogFragmentQuizTitle.text = "오답입니다"
                    updateResolve(current_resolve.pro_id)
                }
            }
            1-> {
                if (answers == viewModel.answerIndex) {
                    binding.textviewDialogFragmentQuizTitle.text = "정답입니다"
                    deleteResolve(current_resolve.pro_id)
                }
                else{
                    binding.textviewDialogFragmentQuizTitle.text = "오답입니다"
                    updateResolve((current_resolve.pro_id))
                }
            }
            2->{
                if (answers == viewModel.quiz.value!!.answer_fi) {
                    binding.textviewDialogFragmentQuizTitle.text = "정답입니다"
                    deleteResolve(current_resolve.pro_id)
                }else{
                    binding.textviewDialogFragmentQuizTitle.text = "오답입니다"
                    updateResolve((current_resolve.pro_id))
                }
            }
            else->{
                if (answers == viewModel.quiz.value!!.answer_fi) {
                    binding.textviewDialogFragmentQuizTitle.text = "정답입니다"
                    deleteResolve(current_resolve.pro_id)
                } else {
                    binding.textviewDialogFragmentQuizTitle.text = "오답입니다"
                    updateResolve(current_resolve.pro_id)
                }
            }
        }
        viewModel.setNextResolve()
    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer(){
        when(flag){
            //그림 보고 낱말 맞추기
            0->{
                if(answers==viewModel.answerIndex){
                    binding.textviewDialogFragmentQuizTitle.text = "정답입니다"
                }else{
                    val resolveRequest = ResolveRequest(
                        viewModel.quiz.value!!.answer_i,viewModel.selectedCategory,-1,
                        viewModel.selectedPCategory,1, App.sharedPreferencesUtil.getUser()?.uid!!,
                        emptyList<Int>(),viewModel.quizIndex.value!!, emptyList<Int>()
                    )
                    insertResolveRequest(resolveRequest)
                    binding.textviewDialogFragmentQuizTitle.text = "정답은 ${viewModel.quiz.value!!.answer_s} 입니다"
                    binding.textviewDialogFragmentQuizTitle.setTextColor(Color.parseColor("#FFEB1635"))
                }
            }
            1-> {
                if (answers == viewModel.answerIndex) {
                    binding.textviewDialogFragmentQuizTitle.text = "정답입니다"
                } else {
                    val resolveRequest = ResolveRequest(
                        viewModel.quiz.value!!.answer_i,
                        viewModel.selectedCategory,
                        -1,
                        viewModel.selectedPCategory,
                        1,
                        App.sharedPreferencesUtil.getUser()?.uid!!,
                        emptyList<Int>(),
                        viewModel.quizIndex.value!!,
                        emptyList<Int>()
                    )
                    insertResolveRequest(resolveRequest)
                    binding.textviewDialogFragmentQuizTitle.text =
                        "정답은 ${viewModel.answerIndex + 1}번 입니다"
                    binding.textviewDialogFragmentQuizTitle.setTextColor(Color.parseColor("#FFEB1635"))
                    Log.i(TAG, "checkAnswer: ${resolveRequest}")
                }

            }
            2->{
                if (answers == viewModel.quiz.value!!.answer_fi) {
                    binding.textviewDialogFragmentQuizTitle.text = "정답입니다"
                }
                else {
                    val resolveRequest = ResolveRequest(
                        viewModel.selectedProblem.value!!.order_id,
                        viewModel.selectedCategory,
                        viewModel.threeSelectedIndexTo.value!!,
                        viewModel.selectedPCategory,
                        1,
                        App.sharedPreferencesUtil.getUser()?.uid!!,
                        emptyList<Int>(),
                        viewModel.quizIndex.value!!,
                        emptyList<Int>()
                    )
                    insertResolveRequest(resolveRequest)
                    binding.textviewDialogFragmentQuizTitle.text =
                        "정답은 ${viewModel.quiz.value!!.answer_fi+1}번 ${App.PICTURES[viewModel.selectedCategory][viewModel.selectedProblem.value!!.order_id]} 입니다"
                    binding.textviewDialogFragmentQuizTitle.setTextColor(Color.parseColor("#FFEB1635"))
                    Log.i(TAG, "checkAnswer: ${resolveRequest}")
                }
            }
            else->{
                if (answers == viewModel.answerIndex) {
                    binding.textviewDialogFragmentQuizTitle.text = "정답입니다"
                } else {
                    val resolveRequest = ResolveRequest(
                        viewModel.quiz.value!!.answer_fi,
                        viewModel.selectedCategory,
                        -1,
                        viewModel.selectedPCategory,
                        1,
                        App.sharedPreferencesUtil.getUser()?.uid!!,
                        viewModel.quizIndex.value!!,
                        viewModel.quiz.value!!.relate_categories,
                        viewModel.relateProblem.value!!
                    )
                    Log.i(TAG, "checkAnswer: ${resolveRequest}")
                    insertResolveRequest(resolveRequest)
                    binding.textviewDialogFragmentQuizTitle.text =
                        "정답은 ${viewModel.quiz.value!!.answer_fi+1}번 ${App.PICTURES[viewModel.selectedCategory][viewModel.quiz.value!!.problems_i[viewModel.quiz.value!!.answer_fi]]} 입니다"
                    binding.textviewDialogFragmentQuizTitle.setTextColor(Color.parseColor("#FFEB1635"))
                }
            }
        }
    }
    private fun updateResolve(id:Int){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = App.resolveRetrofit.create(ResolveService::class.java).updateResolve(id)
        }
    }

    private fun deleteResolve(id:Int){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = App.resolveRetrofit.create(ResolveService::class.java).deleteResolve(id)
        }
    }

    private fun insertResolveRequest(resolveRequest: ResolveRequest){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = App.resolveRetrofit.create(ResolveService::class.java).insertResolve(resolveRequest)
            val body = response.body()
            if(body!=null){
                Log.i(TAG, "insertResolveRequest 성공")
            }else{
                Log.i(TAG, response.message())
            }
        }
    }

    fun onCancleClick(){
        dismiss()
        //오답노트
        if(viewModel.resolveMode){
            mainActivity.popQuizFragment("word")
            mainActivity.popQuizFragment("picture")
            mainActivity.popQuizFragment("blank")
            mainActivity.popQuizFragment("relate")
        }
        //퀴즈
        else {
            mainActivity.popQuizFragment(
                when (viewModel.selectedPCategory) {
                    0 -> "word"
                    1 -> "picture"
                    2 -> "blank"
                    else -> "relate"
                }
            )
        }
        mainActivity.popFragment()
    }
    fun onOkClick(){
        dismiss()
        if(viewModel.resolveMode){
            if(viewModel.resolveIndex.value!!<viewModel.resolve.value!!.size){
             when(viewModel.selectedPCategory){
                 0-> mainActivity.addQuizFragment(QuizWordFragment(),"word")
                 1->mainActivity.addQuizFragment(QuizPictureFragment(),"picture")
                 2->mainActivity.addQuizFragment(QuizBlankFragment(),"blank")
                 else->mainActivity.addQuizFragment(QuizRelateFragment(),"relate")
             }
            }
            else{
                Toast.makeText(requireContext(),"문제의 끝입니다. 전 페이지로 이동합니다",Toast.LENGTH_SHORT).show()
                try{
                    Thread.sleep(2000)
                }catch(e:InterruptedException){
                    e.printStackTrace()
                }
                onCancleClick()
            }
        }
        else{
            when(viewModel.selectedPCategory){
                0-> mainActivity.addQuizFragment(QuizWordFragment(),"word")
                1->mainActivity.addQuizFragment(QuizPictureFragment(),"picture")
                2->mainActivity.addQuizFragment(QuizBlankFragment(),"blank")
                else->mainActivity.addQuizFragment(QuizRelateFragment(),"relate")
            }
        }
        
    }



    /**
     * Dialog Size 설정
     */

    @SuppressLint("ServiceCast")
    fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width:Float, height:Float){
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if(Build.VERSION.SDK_INT<30){
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialogFragment.dialog?.window

            val x = (size.x*width).toInt()
            val y = (size.y*height).toInt()
            window?.setLayout(x,y)
        }else{
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x,y)

        }
}
}