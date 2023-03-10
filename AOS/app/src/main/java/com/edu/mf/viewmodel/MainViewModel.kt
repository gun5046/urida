package com.edu.mf.viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    private var _selectedCategory : Int = -1
    val selectedCategory get() = _selectedCategory


    fun changeCategory(selected:Int){
        _selectedCategory = selected
    }

}