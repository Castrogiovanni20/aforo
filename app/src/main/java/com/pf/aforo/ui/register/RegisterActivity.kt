package com.pf.aforo.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pf.aforo.R
import com.pf.aforo.data.model.UserSupervisor

import com.pf.aforo.databinding.ActivityRegisterBinding
import com.pf.aforo.ui.login.LoginActivity

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
        var firstName = binding.edtName.text.toString()
        var lastName = binding.edtLastName.text.toString()
        var email = binding.edtEmailReg.text.toString()
        var phoneNumber = binding.edtPhone.text.toString()
        var refOrganization = binding.edtOrgReg.text.toString()
        var password = binding.edtPassReg.text.toString()
        var passwordConfirm = binding.edtConfirmarPass.text.toString()
        var role = "SUPERVISOR"

        val user = UserSupervisor(firstName, lastName, email, phoneNumber, refOrganization, password, passwordConfirm, role)

        registerViewModel.registerUser(user)
    }

    private val successObserver = Observer<Any?> { statusCode ->
        when (statusCode) {
            "200" -> {
                Toast.makeText(applicationContext, "Usuario registrado exitosamente. Por favor, inicie sesion.", Toast.LENGTH_SHORT).show()
                initLoginActivity()
            }
        }
    }

    private val failureObserver = Observer<Any?> { statusCode ->
        when (statusCode) {
            "403" -> {
                Toast.makeText(applicationContext, "El usuario y/o la organizacion ya se encuentran registrados.", Toast.LENGTH_SHORT).show()
            }
            "500" -> {
                Toast.makeText(applicationContext, "Por favor, asegurate de completar todos los campos.", Toast.LENGTH_SHORT).show()
            }
            "404" -> {
                Toast.makeText(applicationContext, "Estamos teniendo problemas con nuestro servidor. Por favor intentá registrarte más tarde.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val validationObserver = Observer<Any?> { error ->
        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initLoginActivity () {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java));
    }
}