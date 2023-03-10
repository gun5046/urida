package com.edu.mf.viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    private var _selectedCategory : Int = -1
    val selectedCategory get() = _selectedCategory

    private var _selectedPCategory : Int = -1
    val selectedPCategory get() = _selectedPCategory

    fun changeCategory(selected:Int){
        _selectedCategory = selected
    }

    fun changePCategory(selected:Int){
        _selectedPCategory = selected
    }

}