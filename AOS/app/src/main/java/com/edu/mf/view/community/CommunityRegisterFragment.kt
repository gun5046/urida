package com.edu.mf.view.community

import androidx.appcompat.app.ActionBar
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityRegisterBinding
import com.edu.mf.view.common.MainActivity

class CommunityRegisterFragment: Fragment(), MenuProvider {
    private lateinit var binding: FragmentCommunityRegisterBinding
    private lateinit var mainActivity: MainActivity

    private lateinit var actionBar: ActionBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityRegisterBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar()
    }

    // 액션바 설정
    private fun setActionBar(){
        actionBar = mainActivity.supportActionBar!!
        actionBar.title = ""
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setBackgroundDrawable(
            ContextCompat.getDrawable(requireContext(), R.color.background_light_green)
        )
        actionBar.show()

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.actionbar_register -> {
                mainActivity.popFragment()
                actionBar.hide()
            }
            android.R.id.home->{
                mainActivity.popFragment()
                actionBar.hide()
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actionBar.hide()
    }
}