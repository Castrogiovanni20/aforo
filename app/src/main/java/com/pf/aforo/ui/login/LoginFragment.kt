package com.pf.aforo.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentLoginBinding.bind(view)
        (activity as AppCompatActivity?)!!.actionBar?.hide()
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java);
        setObservers()
        setClickListeners()

    }

    private fun setObservers () {
        loginViewModel.successResponse.observe(viewLifecycleOwner, successObserver)
        loginViewModel.failureResponse.observe(viewLifecycleOwner, failureObserver)
        loginViewModel.validationError.observe(viewLifecycleOwner, validationObserver)
    }

    private fun setClickListeners () {
        binding.btnSend.setOnClickListener{
            login()
        }

        binding.txtCreateAccount.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun login () {
        var userName = binding.edtEmail.text.toString()
        var password = binding.edtPass.text.toString()
        val userLogin = UserLogin(userName, password)

        loginViewModel.loginUser(userLogin)
    }

    private val successObserver = Observer<Any?> { token ->
        setSharedPreferences(token.toString())
        initHomeScreen()
    }

    private val failureObserver = Observer<Any?> { statusCode ->
        when (statusCode) {
            "500", "401" -> {
                Toast.makeText(context, "Usuario y/o contraseña incorrecto.", Toast.LENGTH_SHORT).show()
            }
            "404" -> {
                Toast.makeText(context, "Estamos teniendo problemas con nuestro servidor. Por favor intentá loguearte más tarde.", Toast.LENGTH_SHORT).show()
            }
            "400" -> {
                Toast.makeText(context, "Por favor, completá el usuario y/o contraseña.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private val validationObserver = Observer<Any?> { error ->
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initHomeScreen () {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragmentSupervisor)
    }

    private fun setSharedPreferences(token: String) {
        val sharedPreferences = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("Token", token)
        editor?.putString("Email", binding.edtEmail.text.toString())
        editor?.apply()
    }

}