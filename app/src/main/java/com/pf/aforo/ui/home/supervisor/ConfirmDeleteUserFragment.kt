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
    private val PERMISSION_REFUSED_CODE: String = "PERMISSION_REFUSED"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmDeleteUserBinding.bind(view)
        editUserViewModel = ViewModelProvider(this).get(EditUserViewModel::class.java)
        setClickListeners()
        setObservers()
        setTopBar()
    }

    override fun onResume() {
        super.onResume()
        setConfirmMessage()
    }

    private fun setConfirmMessage() {
        val role = this.arguments?.getString("role")
        val text = if(role == "funcionario") R.string.confirmarDeleteFun else R.string.confirmarDeleteSup
        binding.txtVAviso.setText(text)
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    findNavController().navigate(R.id.action_confirmDeleteUserFragment_to_notificacionesSupervisorFragment)
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

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun clearSharedPreferences() {
        context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.edit()?.clear()?.commit()
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_confirmDeleteUserFragment_to_usuariosSupervisorFragment)
    }

    private fun setClickListeners() {
        binding.btnOK.setOnClickListener {
            deleteUser();
        }

        binding.btnNo.setOnClickListener {
            findNavController().navigate(R.id.action_confirmDeleteUserFragment_to_usuariosSupervisorFragment)
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
        if(statusCode.toString().contains(UNAUTHORIZED_CODE)){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else if(statusCode.toString().contains(PERMISSION_REFUSED_CODE)){
            Toast.makeText(context, "No tiene permisos para realizar esta acción.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_confirmDeleteUserFragment_to_usuariosSupervisorFragment)
        }
        else
            Toast.makeText(context, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }

    private fun deleteUser() {
        val bundle = this.arguments
        val civilServantId = bundle?.getString("civilServantId")
        val refBranchOffice = bundle?.getString("refBranchOffice")

        if (civilServantId != null && refBranchOffice != null) {
            editUserViewModel.removeCivilServant("Bearer ${getToken()}", refBranchOffice, civilServantId)
            editUserViewModel.deleteUser("Bearer ${getToken()}", civilServantId)
        } else if (civilServantId != null ){
            editUserViewModel.deleteUser("Bearer ${getToken()}", civilServantId)
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