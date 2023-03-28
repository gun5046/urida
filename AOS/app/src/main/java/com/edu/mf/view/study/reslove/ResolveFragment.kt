package com.edu.mf.view.study.reslove

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.R
import com.edu.mf.databinding.FragmentResolveBinding
import com.edu.mf.repository.api.ResolveService
import com.edu.mf.repository.model.resolve.ResolveResponse
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.study.learn.LearnMainFragment
import com.edu.mf.view.study.quiz.QuizBlankFragment
import com.edu.mf.view.study.quiz.QuizPictureFragment
import com.edu.mf.view.study.quiz.QuizRelateFragment
import com.edu.mf.view.study.quiz.QuizWordFragment
import com.edu.mf.viewmodel.MainViewModel
import kotlinx.coroutines.*

private const val TAG = "ResolveFragment_지훈"
class ResolveFragment: Fragment() {
    private lateinit var binding: FragmentResolveBinding
    private lateinit var resolveAdapter: ResolveAdapter
    private lateinit var viewModel : MainViewModel
    private lateinit var mainActivity: MainActivity
    var job : Job? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_resolve,container,false)
        init()


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        fetchResolve()
    }

    private fun init(){
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!
        binding.apply{
            handlers = this@ResolveFragment
            vm = viewModel
            lifecycleOwner = this@ResolveFragment
        }
        setAdapter()
    }


    /**
     * 서버로 부터 resolve 데이터 가져오기
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun fetchResolve(){
        job = CoroutineScope(Dispatchers.IO).launch{
            withContext(Dispatchers.Main){
                val response = App.resolveRetrofit.create(ResolveService::class.java).getResolves(App.sharedPreferencesUtil.getUser()?.uid!!)
                if(response.isSuccessful){
                    Log.i(TAG, "fetchResolve: ${response.body()}")
                    resolveAdapter.setData(response.body()!!)
                    resolveAdapter.notifyDataSetChanged()
                    viewModel.setResolve(response.body()!!)
                }else{
                    Log.i(TAG, "fetchResolve error: ${response.message()}")
                }
            }
        }
    }

    /**
     * 어댑터 초기화
     */

    private fun setAdapter(){
       resolveAdapter = ResolveAdapter()
        binding.recyclerviewFragmentResolveList.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = resolveAdapter
        }
        resolveAdapter.setOnResolveClickListener(object : ResolveAdapter.ResolveClickListener{
            override fun onClick(view: View, position: Int, data: ResolveResponse) {
                Log.i(TAG, "정답: ${App.PICTURES[data.category_id][data.answer_id]}")
            }
        })
    }
    fun startSolve(){
        val solves = viewModel.resolve.value!!
        if(solves.size!=0) {
            val solve = solves[0]
            viewModel.changeResolveMode()
            viewModel.setResolveIndex(0)
            Log.i(TAG, "startSolve:${viewModel.resolveIndex}")
            when (solve.type) {
                0 -> mainActivity.addFragment(QuizWordFragment())
                1 -> mainActivity.addFragment(QuizPictureFragment())
                2 -> mainActivity.addFragment(QuizBlankFragment())
                else -> mainActivity.addFragment(QuizRelateFragment())
            }
        }
        else{
            Toast.makeText(requireContext(),"다시 풀어볼 문제가 없습니다",Toast.LENGTH_SHORT).show()
        }
    }



}