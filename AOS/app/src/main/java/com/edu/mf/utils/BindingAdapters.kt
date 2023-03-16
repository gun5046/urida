package com.edu.mf.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.edu.mf.R

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

    @JvmStatic
    @BindingAdapter("imgSrc")
    fun loadImg(view: ImageView, imgSrc: String){
        val imgDrawable = view.context.resources.getIdentifier(
            imgSrc, "drawable", view.context.packageName
        )

        Glide.with(view)
            .load(imgDrawable)
            .into(view)
    }
}