package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.FragmentAddUserBinding

class AddUserFragment : Fragment(R.layout.fragment_add_user) {
    private lateinit var binding: FragmentAddUserBinding
    private lateinit var addUserViewModel: AddUserViewModel
    private val UNAUTHORIZED_CODE: String = "401"
    private val USER_ALREADY_EXISTS_CODE: String = "403"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddUserBinding.bind(view)
        addUserViewModel = ViewModelProvider(this).get(AddUserViewModel::class.java)
        setTopBar()
        setObservers()
        setClickListeners()
        getToken()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    findNavController().navigate(R.id.action_addUserFragment_to_notificacionesSupervisorFragment)
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
            onBackPressed();
        }
    }

    private fun clearSharedPreferences() {
        context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.edit()?.clear()?.commit()
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_addUserFragment_to_usuariosSupervisorFragment)
    }

    private fun setObservers() {
        addUserViewModel.successResponse.observe(viewLifecycleOwner, this.successObserver)
        addUserViewModel.failureResponse.observe(viewLifecycleOwner, this.failureObserver)
        addUserViewModel.validationError.observe(viewLifecycleOwner, this.validationObserver)
    }

    private fun setClickListeners() {
        binding.imgBtnSave.setOnClickListener {
            addUser()
        }
    }

    private fun addUser() {
        var firstName = binding.editTNom.text.toString()
        var lastName = binding.editTApell.text.toString()
        var email = binding.editTextTextEmail.text.toString()
        var identificationNumber = binding.editTextNumberDni.text.toString()
        var phoneNumber = binding.editTextTelefono.text.toString()
        var password = binding.NumPassword.text.toString()
        var passwordConfirm = binding.RepPassword.text.toString()
        var role = "FUNCIONARIO"


        val userFuncionario = UserFuncionario("",firstName, lastName, email, identificationNumber, phoneNumber, password, passwordConfirm, role, null, null, null)

        addUserViewModel.addUser("Bearer " + getToken(), userFuncionario)
    }

    private val successObserver = Observer<Any?> { statusCode ->
        when (statusCode) {
            "200" -> {
                Toast.makeText(context, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addUserFragment_to_usuariosSupervisorFragment)
            }
        }
    }

    private val failureObserver = Observer<Any?> { statusCode ->
        if(statusCode == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesi??n ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else if(statusCode == USER_ALREADY_EXISTS_CODE){
            Toast.makeText(context, "Ya existe un usuario con ese email.", Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(context, "Ocurri?? un error, por favor intent?? nuevamente.", Toast.LENGTH_SHORT).show()
    }

    private val validationObserver = Observer<Any?> { error ->
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_addUserFragment_to_loginFragment)
    }

}