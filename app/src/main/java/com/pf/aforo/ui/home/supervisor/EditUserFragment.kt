package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.FragmentEditUserBinding

class EditUserFragment : Fragment(R.layout.fragment_edit_user) {
   private lateinit var binding: FragmentEditUserBinding
   private lateinit var editUserViewModel: EditUserViewModel
   private lateinit var userFuncionario: UserFuncionario
    private val UNAUTHORIZED_CODE: String = "UNAUTHORIZED"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditUserBinding.bind(view)
        editUserViewModel = ViewModelProvider(this).get(EditUserViewModel::class.java)
        getUserFuncionario()
        setUI()
        setObservers()
        setOnClickListeners()
        setTopBar()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    findNavController().navigate(R.id.action_editUserFragment_to_notificacionesSupervisorFragment)
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
        findNavController().navigate(R.id.action_editUserFragment_to_usuariosSupervisorFragment)
    }

    private fun setUI() {
        binding.fullName.setText(userFuncionario.firstName + " " + userFuncionario.lastName)
        setSpinner()
    }

    private fun setSpinner() {
        val spinner = binding.rolesSpinner
        context?.let {
            ArrayAdapter.createFromResource(it, R.array.roles, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }
    }

    private fun setObservers() {
        editUserViewModel.updateUserRoleSuccessResponse.observe(viewLifecycleOwner, successObserver)
        editUserViewModel.updateUserRoleFailureResponse.observe(viewLifecycleOwner, failureObserver)
    }

    private val successObserver = Observer<Any?> { statusCode ->
        Toast.makeText(context, "Rol actualizado exitosamente.", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_editUserFragment_to_usuariosSupervisorFragment)
    }

    private val failureObserver = Observer<Any?> { statusCode ->
        if(statusCode == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }


    private fun setOnClickListeners() {
        binding.txtVEliminar.setOnClickListener{
            initConfirmDeleteUserScreen()
        }

        binding.btnSiguiente.setOnClickListener {
            val token = "Bearer ${getToken()}"
            val id = userFuncionario.id
            var role: String = ""

            role = if (binding.rolesSpinner.selectedItem.toString() == "FUNCIONARIO") {
                "CIVIL_SERVANT"
            } else {
                binding.rolesSpinner.selectedItem.toString()
            }

            editUserViewModel.updateUserRole(token, id, role)
        }
    }

    private fun initConfirmDeleteUserScreen() {
        val bundle = Bundle()
        bundle.putString("idFuncionario", userFuncionario.id)
        findNavController().navigate(R.id.action_editUserFragment_to_confirmDeleteUserFragment, bundle)
    }

    private fun getUserFuncionario() {
        userFuncionario = arguments?.getParcelable<UserFuncionario>("UserFuncionario")!!
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_editUserFragment_to_loginFragment)
    }
}