package com.pf.aforo.ui.home.funcionario

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentHomeFuncionarioBinding


class HomeFragmentFuncionario : Fragment(R.layout.fragment_home_funcionario) {
    private lateinit var binding: FragmentHomeFuncionarioBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeFuncionarioBinding.bind(view)
        setTopBar()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    initNotificacionesFragments()
                    true
                }
                R.id.itemCerrarSesion -> {
                    initLoginFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun initLoginFragment() {
        findNavController().navigate(R.id.action_homeFragmentFuncionario_to_loginFragment)
    }

    private fun initNotificacionesFragments() {
        findNavController().navigate(R.id.action_homeFragmentFuncionario_to_notificacionesFragment)
    }

}