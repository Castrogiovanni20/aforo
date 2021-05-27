package com.pf.aforo.ui.home.supervisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentSucursalesSupervisorBinding

class SucursalesSupervisorFragment : Fragment(R.layout.fragment_sucursales_supervisor) {
    private lateinit var binding: FragmentSucursalesSupervisorBinding
    private lateinit var sucursalesSupervisorViewModel: SucursalesSupervisorViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSucursalesSupervisorBinding.bind(view)
        sucursalesSupervisorViewModel = ViewModelProvider(this).get(SucursalesSupervisorViewModel::class.java)
        setTopBar()
        setClickListeners()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemFuncionarios -> {
                    findNavController().navigate(R.id.action_sucursalesSupervisorFragment_to_homeFragmentSupervisor)
                    true
                }
                R.id.itemSucursales -> {
                    true
                }
                R.id.itemCerrarSesion -> {
                    findNavController().navigate(R.id.action_sucursalesSupervisorFragment_to_loginFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setClickListeners(){
        binding.btnAgregarSucursal.setOnClickListener {
            findNavController().navigate(R.id.action_sucursalesSupervisorFragment_to_addSucursalFragment)
        }
    }
}