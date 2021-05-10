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
        registerViewModel.successResponse.observe(this, successObserver)
        registerViewModel.failureResponse.observe(this, failureObserver)
    }

    private fun setClickListeners () {
        binding.btnReg.setOnClickListener {
            register()
        }
    }

    private fun register () {
        var fullName = binding.edtName.text.toString() + " " + binding.edtLastName.text.toString()
        var email = binding.edtEmailReg.text.toString()
        //var phoneNumber = binding.edtPhone.text.toString().toInt()
        var phoneNumber = 12323
        var organization = binding.edtOrgReg.text.toString()
        var password = binding.edtPassReg.text.toString()

        val user = User(fullName, email, phoneNumber, organization, password)

        registerViewModel.registerUser(user)
    }

    private val successObserver = Observer<Any?> { statusCode ->
        Log.d("TEST", "HUBO UN CAMBIO")
        if (statusCode == "200") {
            Toast.makeText(applicationContext, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show()
        }
    }

    private val failureObserver = Observer<Any?> { statusCode ->
        Log.d("TEST", "HUBO UN CAMBIO")
        if (statusCode == "403") {
            Toast.makeText(applicationContext, "El usuario ya se encuentra registrado.", Toast.LENGTH_SHORT).show()
        } else if (statusCode == "404") {
            Toast.makeText(applicationContext, "Estamos teniendo problema con nuestro servidor. Por favor intenta registrarte mas tarde.", Toast.LENGTH_SHORT).show()
        }
    }
}