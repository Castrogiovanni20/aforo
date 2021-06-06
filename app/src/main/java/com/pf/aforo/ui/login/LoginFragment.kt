package com.pf.aforo.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.data.model.Data
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.databinding.FragmentLoginBinding
import kotlin.math.log

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
        loginViewModel.loginDataResponseLiveData.observe(viewLifecycleOwner, loginObserver)
        loginViewModel.failureResponse.observe(viewLifecycleOwner, failureObserver)
        loginViewModel.validationError.observe(viewLifecycleOwner, validationObserver)

        loginViewModel.userResponseLiveData.observe(viewLifecycleOwner, userObserver)
        loginViewModel.getUserFailureResponse.observe(viewLifecycleOwner, userFailureObserver)

        loginViewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
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

    private val loginObserver = Observer<Data> { data ->
        val userId = data.userId
        val token = data.token

        loginViewModel.getUser("Bearer $token", userId)
        setSharedPreferences(token)
    }

    private val isLoadingObserver = Observer<Boolean> { flag ->
        if (flag == true) {
            binding.loadingSpinner.visibility = View.VISIBLE
        }
    }

    private val userObserver = Observer<DataUser> { data ->
        when (data.role) {
            "SUPERVISOR" -> initFragmentHomeSupervisor()
            "CIVIL_SERVANT" -> initFragmentHomeFuncionario()
        }
    }

    private val userFailureObserver = Observer<String> {
        Toast.makeText(context, "Algo salio mal", Toast.LENGTH_SHORT).show()
    }

    private val failureObserver = Observer<Any?> { statusCode ->
        binding.loadingSpinner.visibility = View.GONE

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
        binding.loadingSpinner.visibility = View.GONE

        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initFragmentHomeFuncionario() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragmentFuncionario)
    }

    private fun initFragmentHomeSupervisor() {
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