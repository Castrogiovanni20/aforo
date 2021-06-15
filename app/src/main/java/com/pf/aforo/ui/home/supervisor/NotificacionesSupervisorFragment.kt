package com.pf.aforo.ui.home.supervisor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentNotificacionesBinding

class NotificacionesSupervisorFragment : Fragment(R.layout.fragment_notificaciones) {
    private lateinit var binding: FragmentNotificacionesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificacionesBinding.bind(view)
        setTopBar()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemCerrarSesion -> {
                    findNavController().navigate(R.id.action_notificacionesSupervisorFragment_to_loginFragment)
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_notificacionesSupervisorFragment_to_homeFragmentSupervisor)
        }

    }

}