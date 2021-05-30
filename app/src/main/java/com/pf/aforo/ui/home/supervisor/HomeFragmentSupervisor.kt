package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentHomeSupervisorBinding
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class HomeFragmentSupervisor : Fragment(R.layout.fragment_home_supervisor) {

    private var mSocket: Socket? = null
    private lateinit var binding: FragmentHomeSupervisorBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeSupervisorBinding.bind(view)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setTopBar()
        mSocket?.connect()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemFuncionarios -> {
                    initFuncionariosFragments()
                    true
                }
                R.id.itemSucursales -> {
                    initSucursalesFragment()
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

    private fun socket() {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (e: URISyntaxException) {

        }
    }

    private fun initFuncionariosFragments() {
        findNavController().navigate(R.id.action_homeFragmentSupervisor_to_usuariosSupervisorFragment)
    }

    private fun initSucursalesFragment () {
        findNavController().navigate(R.id.action_homeFragmentSupervisor_to_sucursalesSupervisorFragment)
    }

    private fun initLoginFragment() {
        findNavController().navigate(R.id.action_homeFragmentSupervisor_to_loginFragment)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

}