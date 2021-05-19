package com.pf.aforo.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pf.aforo.R
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding
    private lateinit var addUserViewModel: AddUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        setUI()
        setObservers()
        setClickListeners()
        getToken()
    }

    private fun setUI() {
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addUserViewModel = ViewModelProvider(this).get(AddUserViewModel::class.java)
    }

    private fun setObservers() {
        addUserViewModel.successResponse.observe(this, this.successObserver)
        addUserViewModel.failureResponse.observe(this, this.failureObserver)
        addUserViewModel.validationError.observe(this, this.validationObserver)
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
        var phoneNumber = binding.editTextTelefono.text.toString()
        var password = binding.NumPassword.text.toString()
        var refOrganization = "ORT"
        var role = "FUNCIONARIO"
        var token = "Bearer " + this.getToken()

        val userFuncionario = UserFuncionario(firstName, lastName, email, phoneNumber, refOrganization, password, role, token)

        addUserViewModel.addUser(userFuncionario)
    }

    private val successObserver = Observer<Any?> { statusCode ->
        when (statusCode) {
            "200" -> {
                Toast.makeText(applicationContext, "Usuario registrado exitosamente. Por favor, inicie sesion.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val failureObserver = Observer<Any?> { statusCode ->
        Log.d("StatusCode", statusCode.toString())
    }

    private val validationObserver = Observer<Any?> { error ->
        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun getToken(): String {
        val sharedPref = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref.getString("Token", "0").toString()
    }
}