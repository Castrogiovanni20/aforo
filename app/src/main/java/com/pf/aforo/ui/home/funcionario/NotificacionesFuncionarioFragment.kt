package com.pf.aforo.ui.home.funcionario

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentNotificacionesBinding

class NotificacionesFuncionarioFragment : Fragment(R.layout.fragment_notificaciones) {
    private lateinit var binding: FragmentNotificacionesBinding
    private lateinit var notificacionesFuncionarioViewModel: NotificacionesFuncionarioViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificacionesBinding.bind(view)
        notificacionesFuncionarioViewModel = ViewModelProvider(this).get(NotificacionesFuncionarioViewModel::class.java)
        setTopBar()
        getUserSettings()
        setOnClickListeners()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemCerrarSesion -> {
                    initLoginFragment()
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_notificacionesFragment_to_homeFragmentFuncionario)
        }
    }

    private fun setOnClickListeners() {
        binding.btnSiguiente.setOnClickListener {
            saveSettings()
        }
    }

    private fun saveSettings() {
        //val fullCapacityAlert = binding.switchCapacidadAlta.text
        //val highCapacityLevelAlert = binding.switchCapacidadAlta.text
    }

    private fun getUserSettings() {
       // notificacionesFuncionarioViewModel.getUserSettings("Bearer ${getToken()}")
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_notificacionesFragment_to_loginFragment)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }
}