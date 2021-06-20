package com.pf.aforo.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.data.model.Settings
import com.pf.aforo.data.model.UserSettings
import com.pf.aforo.data.model.UserSupervisor
import com.pf.aforo.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentRegisterBinding.bind(view)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        setObservers()
        setClickListeners()
    }

    private fun setObservers () {
        registerViewModel.successResponse.observe(viewLifecycleOwner, this.successObserver)
        registerViewModel.failureResponse.observe(viewLifecycleOwner, this.failureObserver)
        registerViewModel.validationError.observe(viewLifecycleOwner, this.validationObserver)
        registerViewModel.startProgressBar.observe(viewLifecycleOwner, this.startProgressBarObserver)
        registerViewModel.stopProgressBar.observe(viewLifecycleOwner, this.stopProgressBarObserver)
    }

    private fun setClickListeners () {
        binding.btnReg.setOnClickListener {
            register()
        }
    }

    private fun register () {
        var firstName = binding.edtName.text.toString()
        var lastName = binding.edtLastName.text.toString()
        var email = binding.edtEmailReg.text.toString()
        var phoneNumber = binding.edtPhone.text.toString()
        var refOrganization = binding.edtOrgReg.text.toString()
        var password = binding.edtPassReg.text.toString()
        var passwordConfirm = binding.edtConfirmarPass.text.toString()
        var role = "SUPERVISOR"
        var settings = Settings(false, false)

        val user = UserSupervisor(firstName, lastName, email, phoneNumber, refOrganization, password, passwordConfirm, role, settings)

        registerViewModel.startProgressBar()
        registerViewModel.registerUser(user)
    }

    private val successObserver = Observer<Any?> { statusCode ->
        registerViewModel.stopProgressBar()
        when (statusCode) {
            "200" -> {
                Toast.makeText(context, "Usuario registrado exitosamente. Por favor, inicie sesion.", Toast.LENGTH_SHORT).show()
                initLoginFragment()
            }
        }
    }

    private val failureObserver = Observer<Any?> { statusCode ->
        registerViewModel.stopProgressBar()
        when (statusCode) {
            "403" -> {
                Toast.makeText(context, "El usuario y/o la organizacion ya se encuentran registrados.", Toast.LENGTH_SHORT).show()
            }
            "500" -> {
                Toast.makeText(context, "Por favor, asegurate de completar todos los campos.", Toast.LENGTH_SHORT).show()
            }
            "404" -> {
                Toast.makeText(context, "Estamos teniendo problemas con nuestro servidor. Por favor intentá registrarte más tarde.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val startProgressBarObserver = Observer<Boolean> {
        binding.loadingSpinner.visibility = View.VISIBLE
    }

    private val stopProgressBarObserver = Observer<Boolean> {
        binding.loadingSpinner.visibility = View.GONE
    }

    private val validationObserver = Observer<Any?> { error ->
        registerViewModel.stopProgressBar()
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initLoginFragment () {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

}