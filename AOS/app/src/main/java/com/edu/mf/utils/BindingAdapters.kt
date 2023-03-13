package com.edu.mf.utils

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("leftVisible")
    fun setLeftVisible(view: View,isVisible:Boolean){
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("rightVisible")
    fun setRightVisible(view:View,isVisible: Boolean){
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

}