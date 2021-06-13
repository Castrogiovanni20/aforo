package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.FragmentEditSucursalBinding

class EditSucursalFragment : Fragment(R.layout.fragment_edit_sucursal) {
    private lateinit var binding: FragmentEditSucursalBinding
    private lateinit var editSucursalViewModel: EditSucursalViewModel
    private lateinit var branchOffice: BranchOffice
    private lateinit var currentUser: UserFuncionario
    private var listUserFuncionarios = ArrayList<UserFuncionario>()
    private var fullnameSpinnerArray = ArrayList<String>()
    private var userIdSelected = "null"
    private val UNAUTHORIZED_CODE: String = "401"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditSucursalBinding.bind(view)
        editSucursalViewModel = ViewModelProvider(this).get(EditSucursalViewModel::class.java)
        setTopBar()
        getUsersFuncionarios()
        getBranchOffice()
        setObservers()
        setUI()
        setClickListeners()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemFuncionarios -> {
                    findNavController().navigate(R.id.action_editSucursalFragment_to_sucursalesSupervisorFragment)
                    true
                }
                R.id.itemCerrarSesion -> {
                    initLoginFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun setUI() {
        binding.edtNombreSucursal.setText(branchOffice.name)
        binding.edtOrganizacion.setText(branchOffice.refOrganization)
        binding.edtDomicilio.setText(branchOffice.description)
        binding.edtMt2Ancho.setText(branchOffice.width.toString())
        binding.edtMt2Alto.setText(branchOffice.length.toString())
        binding.textFuncionarioAsignado.text = "Funcionario asignado: " + branchOffice.refUser
    }

    private fun setSpinner() {
        val spinner = binding.spinnerFuncionario
        if (fullnameSpinnerArray.isNotEmpty()) {
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_expandable_list_item_1,
                    fullnameSpinnerArray
                )
            }
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    userIdSelected = listUserFuncionarios[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        } else {
            binding.spinnerFuncionario.visibility = View.GONE
        }

    }

    private fun setObservers() {
        editSucursalViewModel.updateBranchOfficeSuccessResponse.observe(viewLifecycleOwner, successObserver)
        editSucursalViewModel.updateBranchOfficeFailureResponse.observe(viewLifecycleOwner, failureObserver)
        editSucursalViewModel.getUsersSuccessResponse.observe(viewLifecycleOwner, usersObserver)
    }

    private fun setClickListeners() {
        binding.btnSiguiente.setOnClickListener {
            editBranchOffice()
        }

        binding.textViewEliminar.setOnClickListener {
            initConfirmDeleteSucursalScreen(branchOffice.id)
        }
    }

    private fun editBranchOffice() {
        val id = branchOffice.id
        val name = binding.edtNombreSucursal.text.toString()
        val refOrganization = binding.edtNombreSucursal.text.toString()
        val description = binding.edtDomicilio.text.toString()
        val width = Integer.parseInt(binding.edtMt2Ancho.text.toString())
        val length = Integer.parseInt(binding.edtMt2Alto.text.toString())
        val refUser = binding.textFuncionarioAsignado.text.toString()

        val newBranchOffice = BranchOffice("", id, refOrganization, name, description, refUser, 0, width, length, 0)
        editSucursalViewModel.updateBranchOffice("Bearer ${getToken()}", newBranchOffice.id, newBranchOffice)
    }

    private val successObserver = Observer<Any?> { statusCode ->
        Toast.makeText(context, "Sucursal actualizada exitosamente.", Toast.LENGTH_SHORT).show()
        initSucursalesSupervisorScreen()
    }

    private val failureObserver = Observer<Any?> {statusCode ->
        if(statusCode == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }

    private val usersObserver = Observer<Array<DataUser>> { dataUser ->
        for (user in dataUser) {
            if (user.role == "CIVIL_SERVANT" && user.refBranchOffice == null) {
                val userFuncionario = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.phoneNumber, user.password, user.role)
                val fullname = userFuncionario.firstName + " " + userFuncionario.lastName
                listUserFuncionarios.add(userFuncionario)
                fullnameSpinnerArray.add(fullname)
            }

            if (user.id == branchOffice.refUser) {
                currentUser = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.phoneNumber, user.password, user.role)
            }
        }

        setSpinner()
    }

    private fun getUsersFuncionarios() {
        editSucursalViewModel.getUsers("Bearer ${getToken()}")
    }

    private fun getBranchOffice() {
       branchOffice = arguments?.getParcelable<BranchOffice>("BranchOffice")!!
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun initSucursalesSupervisorScreen() {
        findNavController().navigate(R.id.action_editSucursalFragment_to_sucursalesSupervisorFragment)
    }

    private fun initConfirmDeleteSucursalScreen(idBranchOffice: String) {
        val bundle = Bundle()
        bundle.putString("idBranchOffice", idBranchOffice)
        findNavController().navigate(R.id.action_editSucursalFragment_to_confirmDeleteSucursalFragment, bundle)
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_editSucursalFragment_to_loginFragment)
    }
}