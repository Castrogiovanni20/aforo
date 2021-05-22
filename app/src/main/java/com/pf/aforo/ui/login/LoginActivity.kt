package com.pf.aforo.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pf.aforo.R
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.databinding.ActivityLoginBinding
import com.pf.aforo.ui.home.supervisor.HomeActivity
import com.pf.aforo.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUI() //Setear elementos de la vista: databinding y viewmodel
        setObservers() //Patrón observer: suscripción
        setClickListeners() //Eventos de botón
    }

    private fun setUI () {
        binding = ActivityLoginBinding.inflate(layoutInflater);
        setContentView(binding.root);
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java);

    }

    private fun setObservers () {
        loginViewModel.successResponse.observe(this, successObserver)
        loginViewModel.failureResponse.observe(this, failureObserver)
        loginViewModel.validationError.observe(this, validationObserver)
    }

    private fun setClickListeners () {
        binding.btnSend.setOnClickListener{
            login()
        }

        binding.txtCreateAccount.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun login () {
        var userName = binding.edtEmail.text.toString()
        var password = binding.edtPass.text.toString()
        val userLogin = UserLogin(userName, password)

        loginViewModel.loginUser(userLogin)
    }

    private val successObserver = Observer<Any?> { token ->
        saveToken(token.toString())
        initHomeScreen()
    }

    //El viewmodel actualiza la variable observable, cuando hay un cambio, la activity se entera de ese cambio:
    private val failureObserver = Observer<Any?> { statusCode ->
        when (statusCode) {
            "500", "401" -> {
                Toast.makeText(applicationContext, "Usuario y/o contraseña incorrecto.", Toast.LENGTH_SHORT).show()
            }
            "404" -> {
                Toast.makeText(applicationContext, "Estamos teniendo problemas con nuestro servidor. Por favor intentá loguearte más tarde.", Toast.LENGTH_SHORT).show()
            }
            "400" -> {
                Toast.makeText(applicationContext, "Por favor, completá el usuario y/o contraseña.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private val validationObserver = Observer<Any?> { error ->
        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initHomeScreen () {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java));
    }

    private fun saveToken (token: String) {
        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Token", token)
        editor.apply()
    }

}