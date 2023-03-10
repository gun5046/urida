package com.edu.mf.view.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.ActivityMainBinding
import com.edu.mf.view.MainFragment
import com.edu.mf.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    init {
        instance = this
    }

    companion object{
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity? {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        changeFragment(MainFragment())
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.framelayout_main, fragment)
            .commit()
    }

    fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.framelayout_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}