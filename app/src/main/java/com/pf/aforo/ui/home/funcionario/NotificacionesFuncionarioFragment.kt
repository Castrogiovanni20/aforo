package com.pf.aforo.ui.home.funcionario

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import com.pf.aforo.R
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.Settings
import com.pf.aforo.databinding.FragmentNotificacionesBinding

class NotificacionesFuncionarioFragment : Fragment(R.layout.fragment_notificaciones) {
    private lateinit var binding: FragmentNotificacionesBinding
    private lateinit var notificacionesFuncionarioViewModel: NotificacionesFuncionarioViewModel
    private val UNAUTHORIZED_CODE: String = "401"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificacionesBinding.bind(view)
        notificacionesFuncionarioViewModel = ViewModelProvider(this).get(NotificacionesFuncionarioViewModel::class.java)
        setTopBar()
        getUser()
        setObservers()
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

    private fun setObservers() {
        notificacionesFuncionarioViewModel.getUserSuccessResponse.observe(viewLifecycleOwner, getUserSuccessObserver)
        notificacionesFuncionarioViewModel.getUserFailureResponse.observe(viewLifecycleOwner, getUserFailureObserver)
        notificacionesFuncionarioViewModel.updateSettingsSuccessResponse.observe(viewLifecycleOwner, updateSettingsSuccessObserver)
        notificacionesFuncionarioViewModel.updateSettingsFailureResponse.observe(viewLifecycleOwner, updateSettingsFailureObserver)
    }

    val getUserSuccessObserver = Observer<DataUser> { it ->
        setUI(it.settings)
    }

    val getUserFailureObserver = Observer<String> { error ->
        onError(error)
    }

    val updateSettingsSuccessObserver = Observer<String> {
        Toast.makeText(context, "Ajustes guardados exitosamente.", Toast.LENGTH_SHORT).show()
    }

    val updateSettingsFailureObserver = Observer<String> { error ->
        onError(error)
    }

    private fun setOnClickListeners() {
        binding.btnSiguiente.setOnClickListener {
            saveSettings()
        }
    }

    private fun saveSettings() {
        val userId = getUserId()
        val highCapacityLevelAlert = binding.switchCapacidadAlta.isChecked
        val fullCapacityAlert = binding.switchCapacidadMaxima.isChecked
        val settings = Settings(fullCapacityAlert, highCapacityLevelAlert)

        notificacionesFuncionarioViewModel.updateSettings("Bearer ${getToken()}", userId, settings)
    }

    private fun setUI(userSettings: Settings) {
        binding.switchCapacidadAlta.isChecked = userSettings.highCapacityLevelAlert
        binding.switchCapacidadMaxima.isChecked = userSettings.fullCapacityAlert
    }

    private fun getUser() {
       notificacionesFuncionarioViewModel.getUser("Bearer ${getToken()}", getUserId())
    }

    private fun onError(error: Any?){
        if(error == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, "Ha ocurrido un error." + error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_notificacionesFragment_to_loginFragment)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun getUserId(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("UserID", "0").toString()
    }
}