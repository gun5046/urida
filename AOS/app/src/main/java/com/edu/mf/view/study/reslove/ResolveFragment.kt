package com.edu.mf.view.study.reslove

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.R
import com.edu.mf.databinding.FragmentResolveBinding
import com.edu.mf.repository.api.ResolveService
import com.edu.mf.repository.model.resolve.ResolveResponse
import com.edu.mf.utils.App
import kotlinx.coroutines.*

private const val TAG = "ResolveFragment_지훈"
class ResolveFragment: Fragment() {
    private lateinit var binding: FragmentResolveBinding
    private lateinit var resolveAdapter: ResolveAdapter
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
                }else{
                    Log.i(TAG, "fetchResolve: ${response.message()}")
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
            override fun onClick(view: View, position: Int) {
                Log.i(TAG, "onClick: ${position}")
            }

        })
    }




}