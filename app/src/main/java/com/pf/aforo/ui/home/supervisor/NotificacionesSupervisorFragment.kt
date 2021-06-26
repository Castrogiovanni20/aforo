package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.Settings
import com.pf.aforo.data.model.UserSettings
import com.pf.aforo.databinding.FragmentNotificacionesBinding

class NotificacionesSupervisorFragment : Fragment(R.layout.fragment_notificaciones) {
    private lateinit var binding: FragmentNotificacionesBinding
    private lateinit var notificacionesSupervisorViewModel: NotificacionesSupervisorViewModel
    private val UNAUTHORIZED_CODE: String = "401"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificacionesBinding.bind(view)
        notificacionesSupervisorViewModel = ViewModelProvider(this).get(NotificacionesSupervisorViewModel::class.java)
        setTopBar()
        getUser()
        setObservers()
        setOnClickListeners()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemCerrarSesion -> {
                    clearSharedPreferences()
                    findNavController().navigate(R.id.action_notificacionesSupervisorFragment_to_loginFragment)
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun setObservers() {
        notificacionesSupervisorViewModel.getUserSuccessResponse.observe(viewLifecycleOwner, getUserSuccessObserver)
        notificacionesSupervisorViewModel.getUserFailureResponse.observe(viewLifecycleOwner, getUserFailureObserver)
        notificacionesSupervisorViewModel.updateSettingsSuccessResponse.observe(viewLifecycleOwner, updateSettingsSuccessObserver)
        notificacionesSupervisorViewModel.updateSettingsFailureResponse.observe(viewLifecycleOwner, updateSettingsFailureObserver)
    }

    val getUserSuccessObserver = Observer<DataUser> { it ->
        val userSettings = UserSettings(it.settings)
        setUI(userSettings)
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
        val userSettings = UserSettings(settings)
        notificacionesSupervisorViewModel.updateSettings("Bearer ${getToken()}", userId, userSettings)
    }

    private fun setUI(userSettings: UserSettings) {
        binding.switchCapacidadAlta.isChecked = userSettings.settings.highCapacityLevelAlert
        binding.switchCapacidadMaxima.isChecked = userSettings.settings.fullCapacityAlert
    }

    private fun getUser() {
        notificacionesSupervisorViewModel.getUser("Bearer ${getToken()}", getUserId())
    }

    private fun onError(error: Any?){
        if(error == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, "Ha ocurrido un error." + error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun getUserId(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("UserID", "0").toString()
    }

    private fun clearSharedPreferences() {
        context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.edit()?.clear()?.commit()
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_notificacionesSupervisorFragment_to_loginFragment)
    }

}