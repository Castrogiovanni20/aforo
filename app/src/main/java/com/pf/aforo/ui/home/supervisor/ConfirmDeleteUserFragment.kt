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
import com.pf.aforo.databinding.FragmentConfirmDeleteUserBinding

class ConfirmDeleteUserFragment : Fragment(R.layout.fragment_confirm_delete_user) {
    private lateinit var binding: FragmentConfirmDeleteUserBinding
    private lateinit var editUserViewModel: EditUserViewModel
    private val UNAUTHORIZED_CODE: String = "UNAUTHORIZED"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmDeleteUserBinding.bind(view)
        editUserViewModel = ViewModelProvider(this).get(EditUserViewModel::class.java)
        setClickListeners()
        setObservers()
        setTopBar()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    findNavController().navigate(R.id.action_confirmDeleteUserFragment_to_notificacionesSupervisorFragment)
                    true
                }
                R.id.itemCerrarSesion -> {
                    initLoginFragment()
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_confirmDeleteUserFragment_to_usuariosSupervisorFragment)
    }

    private fun setClickListeners() {
        binding.btnOK.setOnClickListener {
            deleteUser();
        }
    }

    private fun setObservers(){
        editUserViewModel.deleteUserSuccessResponse.observe(viewLifecycleOwner, successObserver)
        editUserViewModel.deleteUserFailureResponse.observe(viewLifecycleOwner, failureObserver)
    }

    private val successObserver = Observer<Any> {
        Toast.makeText(context, "Usuario eliminado exitosamente.", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_confirmDeleteUserFragment_to_usuariosSupervisorFragment)
    }

    private val failureObserver = Observer<Any> { statusCode ->
        if(statusCode == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }

    private fun deleteUser() {
        val bundle = this.arguments
        val idFuncionario = bundle?.getString("idFuncionario")
        if (idFuncionario != null) {
            editUserViewModel.deleteUser("Bearer ${getToken()}", idFuncionario)
        }
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_confirmDeleteUserFragment_to_loginFragment)
    }

}