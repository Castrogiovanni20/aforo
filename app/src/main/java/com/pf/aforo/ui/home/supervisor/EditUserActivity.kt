package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pf.aforo.R
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.ActivityEditFuncBinding

class EditUserActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditFuncBinding
    private lateinit var editViewModel: EditUserViewModel
    private lateinit var userFuncionario: UserFuncionario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_func)
        getUserFuncionario()
        setUI()
        setObservers()
        setOnClickListeners()
    }

    private fun setUI() {
        binding = ActivityEditFuncBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editViewModel = ViewModelProvider(this).get(EditUserViewModel::class.java)

        binding.edtNombre.setText(userFuncionario.firstName)
        binding.edtApellido.setText(userFuncionario.lastName)
        binding.edtTelefono.setText(userFuncionario.phoneNumber)
        binding.edtMail.setText(userFuncionario.email)
        binding.edtContrasenia.setText(userFuncionario.password)
    }

    private fun setObservers() {
        editViewModel.deleteUserSuccessResponse.observe(this, deleteUserSuccessObserver)
        editViewModel.deleteUserFailureResponse.observe(this, deleteUserFailureObserver)
        editViewModel.updateUserSuccessResponse.observe(this, updateUserSuccessObserver)
        editViewModel.updateUserFailureResponse.observe(this, updateUserFailureObserver)
    }

    private fun setOnClickListeners() {
        binding.btnSiguiente.setOnClickListener {
            editFuncionario()
        }

        binding.txtVEliminar.setOnClickListener{
            deleteFuncionario()
        }
    }

    private fun editFuncionario() {
        var id = userFuncionario.id
        var firstName = binding.edtNombre.text.toString()
        var lastName = binding.edtApellido.text.toString()
        var email = binding.edtMail.text.toString()
        var phoneNumber = binding.edtTelefono.text.toString()
        var password = binding.edtContrasenia.text.toString()
        var role = userFuncionario.role

        val userFuncionario = UserFuncionario(id, firstName, lastName, email, phoneNumber, password, role)
        editViewModel.updateUser("Bearer " + getToken(), userFuncionario)
    }

    private fun deleteFuncionario() {
        editViewModel.deleteUser("Bearer " + getToken(), userFuncionario.id)
    }

    private val deleteUserSuccessObserver = Observer<Any?> { statusCode ->
        Toast.makeText(applicationContext, "Usuario eliminado exitosamente.", Toast.LENGTH_SHORT).show()
        initHomeScreen()
    }

    private val deleteUserFailureObserver = Observer<Any> { statusCode ->
        Toast.makeText(applicationContext, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }

    private val updateUserSuccessObserver = Observer<Any?> { statusCode ->
        Toast.makeText(applicationContext, "Usuario actualizado exitosamente.", Toast.LENGTH_SHORT).show()
        initHomeScreen()
    }

    private val updateUserFailureObserver = Observer<Any?> { statusCode ->
        Toast.makeText(applicationContext, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }


    private fun getToken(): String {
        val sharedPref = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref.getString("Token", "0").toString()
    }

    private fun getUserFuncionario() {
        userFuncionario = intent.extras?.get("UserFuncionario") as UserFuncionario
    }

    private fun initHomeScreen() {
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

}