package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.FragmentEditProfileBinding
import com.pf.aforo.databinding.FragmentEditUserBinding

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private lateinit var binding : FragmentEditProfileBinding
    private lateinit var editProfileViewModel: EditProfileViewModel
    private lateinit var userFuncionario: UserFuncionario
    private val UNAUTHORIZED_CODE: String = "401"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        editProfileViewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        setTopBar()
        setNavigation()
        getUser()
        setObservers()
        setOnClickListeners()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    findNavController().navigate(R.id.action_editProfileFragment_to_notificacionesSupervisorFragment)
                    true
                }
                R.id.itemCerrarSesion -> {
                    initLoginFragment()
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setNavigation(){
        binding.bottomNavigation.selectedItemId = R.id.itemPerfil

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemHome -> {
                    findNavController().navigate(R.id.action_editProfileFragment_to_homeFragmentSupervisor)
                    true
                }
                R.id.itemFuncionarios -> {
                    findNavController().navigate(R.id.action_editProfileFragment_to_usuariosSupervisorFragment)
                    true
                }
                R.id.itemSucursales -> {
                    findNavController().navigate(R.id.action_editProfileFragment_to_sucursalesSupervisorFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_editProfileFragment_to_usuariosSupervisorFragment)
    }

    private fun setUI() {
        binding.edtNombre.setText(userFuncionario.firstName)
        binding.edtApellido.setText(userFuncionario.lastName)
        binding.edtTelefono.setText(userFuncionario.phoneNumber)
        binding.edtMail.setText(userFuncionario.email)
    }

    private fun setObservers() {
        editProfileViewModel.getUserSuccessResponse.observe(viewLifecycleOwner, getUserSuccessObserver)
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
        var role = userFuncionario.role

        val userFuncionario = UserFuncionario(id, firstName, lastName, email, phoneNumber, "", role)
        editProfileViewModel.updateUser("Bearer ${getToken()}" , userFuncionario)
    }

    private val getUserSuccessObserver = Observer<DataUser> { data ->
        userFuncionario = UserFuncionario(data.id, data.firstName, data.lastName, data.email, data.phoneNumber, "", data.role)
        setUI()
    }

    private val updateUserSuccessObserver = Observer<Any?> { statusCode ->
        Toast.makeText(context, "Perfil actualizado exitosamente.", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_editProfileFragment_to_usuariosSupervisorFragment)
    }

    private val updateUserFailureObserver = Observer<Any?> { statusCode ->
        if(statusCode == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun getUserId(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("UserID", "0").toString()
    }

    private fun getUser() {
        val token = getToken()
        val id = getUserId()
        editProfileViewModel.getUser("Bearer $token", id)
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_editProfileFragment_to_loginFragment)
    }

}