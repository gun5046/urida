package com.edu.mf.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.repository.db.ProblemRepository

class MainViewModelFactory(private val repository: ProblemRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository) as T
        }
        throw IllegalAccessError("Unkown ViewModel Class")
    }
}