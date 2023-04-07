package com.edu.mf.view.common

import android.os.SystemClock
import android.view.View

class OnSingleClickListener(
    private var interval: Int = 1500,
    private var onSingleClick: (View) -> Unit
): View.OnClickListener {
    private var lastClickTime: Long = 0

    override fun onClick(p0: View?) {
        val elapsedRealtime = SystemClock.elapsedRealtime()
        if ((elapsedRealtime - lastClickTime) < interval){
            return
        }
        lastClickTime = elapsedRealtime
        onSingleClick(p0!!)
    }
}