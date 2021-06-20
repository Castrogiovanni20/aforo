package com.pf.aforo.ui.home.funcionario

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentHomeFuncionarioSinSucursalBinding


class HomeFragmentFuncionarioSinSucursal : Fragment(R.layout.fragment_home_funcionario_sin_sucursal) {
    private lateinit var binding: FragmentHomeFuncionarioSinSucursalBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeFuncionarioSinSucursalBinding.bind(view)
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
                    clearSharedPreferences()
                    initLoginFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun clearSharedPreferences() {
        context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.edit()?.clear()?.commit()
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_homeFragmentFuncionarioSinSucursal_to_loginFragment)
    }
//
    private fun initNotificacionesFragments() {
        findNavController().navigate(R.id.action_homeFragmentFuncionarioSinSucursal_to_notificacionesFragment)
    }

}


