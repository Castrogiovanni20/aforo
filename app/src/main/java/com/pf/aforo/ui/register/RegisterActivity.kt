package com.pf.aforo.ui.register

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pf.aforo.R
import com.pf.aforo.data.model.User

import com.pf.aforo.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setUI()
        setObservers()
        setClickListeners()
    }

    private fun setUI () {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    private fun setObservers () {
        registerViewModel.successResponse.observe(this, this.successObserver)
        registerViewModel.failureResponse.observe(this, this.failureObserver)
        registerViewModel.validationError.observe(this, this.validationObserver)
    }

    private fun setClickListeners () {
        binding.btnReg.setOnClickListener {
            register()
        }
    }

    private fun register () {
        var fullName = binding.edtName.text.toString() + " " + binding.edtLastName.text.toString()
        var email = binding.edtEmailReg.text.toString()
        var phoneNumber = if (binding.edtPhone.text.toString() != "") (binding.edtPhone.text.toString().toInt()) else (0)
        var organization = binding.edtOrgReg.text.toString()
        var password = binding.edtPassReg.text.toString()

        val user = User(fullName, email, phoneNumber, organization, password)

        registerViewModel.registerUser(user)
    }

    private val successObserver = Observer<Any?> { statusCode ->
        when (statusCode) {
            "200" -> {
                Toast.makeText(applicationContext, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val failureObserver = Observer<Any?> { statusCode ->
        when (statusCode) {
            "403" -> {
                Toast.makeText(applicationContext, "El usuario ya se encuentra registrado.", Toast.LENGTH_SHORT).show()
            }
            "500" -> {
                Toast.makeText(applicationContext, "Por favor, asegurate de completar todos los campos.", Toast.LENGTH_SHORT).show()
            }
            "404" -> {
                Toast.makeText(applicationContext, "Estamos teniendo problema con nuestro servidor. Por favor intenta registrarte mas tarde.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val validationObserver = Observer<Any?> { error ->
        when (error) {
            "FullName" -> {
                Toast.makeText(applicationContext, "El nombre completo debe ser mayor a 5 y menor a 15 caracteres.", Toast.LENGTH_SHORT).show()
            }
            "Email" -> {
                Toast.makeText(applicationContext, "El email debe ser mayor o igual a 10 caracteres.", Toast.LENGTH_SHORT).show()
            }
            "Phone" -> {
                Toast.makeText(applicationContext, "El telefono debe ser mayor o igual a 8.", Toast.LENGTH_SHORT).show()
            }
            "Organization" -> {
                Toast.makeText(applicationContext, "La organizacion debe ser mayor o igual a 2 caracteres.", Toast.LENGTH_SHORT).show()
            }
            "Password" -> {
                Toast.makeText(applicationContext, "La contraseña debe ser mayor o igual a 8 caracteres.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}