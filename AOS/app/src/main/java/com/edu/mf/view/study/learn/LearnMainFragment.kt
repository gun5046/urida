package com.edu.mf.view.study.learn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edu.mf.R


/**
 * A simple [Fragment] subclass.
 * Use the [LearnMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LearnMainFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn_main, container, false)
    }

    
}