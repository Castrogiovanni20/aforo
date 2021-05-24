package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.FragmentEditProfileBinding
import com.pf.aforo.databinding.FragmentEditUserBinding

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private lateinit var binding : FragmentEditProfileBinding
    private lateinit var editProfileViewModel: EditProfileViewModel
    private lateinit var userFuncionario: UserFuncionario

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        editProfileViewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        getUser()
        setUI()
        setObservers()
        setOnClickListeners()
    }

    private fun setUI() {
        binding.edtNombre.setText(userFuncionario.firstName)
        binding.edtApellido.setText(userFuncionario.lastName)
        binding.edtTelefono.setText(userFuncionario.phoneNumber)
        binding.edtMail.setText(userFuncionario.email)
        binding.edtContrasenia.setText(userFuncionario.password)
    }

    private fun setObservers() {
        editProfileViewModel.updateUserSuccessResponse.observe(viewLifecycleOwner, updateUserSuccessObserver)
        editProfileViewModel.updateUserFailureResponse.observe(viewLifecycleOwner, updateUserFailureObserver)
    }

    private fun setOnClickListeners() {
        binding.btnSiguiente.setOnClickListener {
            editFuncionario()
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
        editProfileViewModel.updateUser("Bearer ${getToken()}" , userFuncionario)
    }

    private val updateUserSuccessObserver = Observer<Any?> { statusCode ->
        Toast.makeText(context, "Perfil actualizado exitosamente.", Toast.LENGTH_SHORT).show()
        initHomeScreen()
    }

    private val updateUserFailureObserver = Observer<Any?> { statusCode ->
        Toast.makeText(context, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun getUser() {
        userFuncionario = arguments?.getParcelable("UserFuncionario")!!
    }

    private fun initHomeScreen() {
        val bundle = Bundle()
        bundle.putString("Email", userFuncionario.email)
        findNavController().navigate(R.id.action_editProfileFragment_to_homeFragmentSupervisor, bundle)
    }


}