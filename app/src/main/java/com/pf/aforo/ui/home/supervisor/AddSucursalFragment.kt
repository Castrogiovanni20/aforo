package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
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
import com.pf.aforo.databinding.FragmentAddSucursalBinding

class AddSucursalFragment : Fragment(R.layout.fragment_add_sucursal) {
    private lateinit var binding: FragmentAddSucursalBinding
    private lateinit var addSucursalViewModel: AddSucursalViewModel
    private var listUserFuncionarios = ArrayList<UserFuncionario>()
    private var fullnameSpinnerArray = ArrayList<String>()
    private var userIdSelected = "null"
    private val UNAUTHORIZED_CODE: String = "401"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddSucursalBinding.bind(view)
        addSucursalViewModel = ViewModelProvider(this).get(AddSucursalViewModel::class.java)
        setTopBar()
        getUsersFuncionarios()
        setObservers()
        setClickListeners()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    findNavController().navigate(R.id.action_addSucursalFragment_to_notificacionesSupervisorFragment)
                    true
                }
                R.id.itemCerrarSesion -> {
                    clearSharedPreferences()
                    initLoginFragment()
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed();
        }
    }

    private fun clearSharedPreferences() {
        context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.edit()?.clear()?.commit()
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_addSucursalFragment_to_sucursalesSupervisorFragment)
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
            spinner.setSelection(0,false)

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        userIdSelected = listUserFuncionarios[position-1].id
                        binding.textFuncionarioAsignado.text = "Funcionario asignado: " + listUserFuncionarios[position-1].firstName + " " + listUserFuncionarios[position-1].lastName
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        } else {
            binding.textFuncionarioAsignado.text = "No hay funcionarios disponibles."
            binding.spinnerFuncionario.visibility = View.GONE
        }
    }

    private fun setObservers() {
        addSucursalViewModel.validationError.observe(viewLifecycleOwner, this.validationObserver)
        addSucursalViewModel.addBranchOfficeSuccessResponse.observe(viewLifecycleOwner, this.successObserver)
        addSucursalViewModel.addBranchOfficeFailureResponse.observe(viewLifecycleOwner, this.failureObserver)
        addSucursalViewModel.getUsersSuccessResponse.observe(viewLifecycleOwner, usersObserver)
        addSucursalViewModel.assignCivilServantSuccessResponse.observe(viewLifecycleOwner, this.assignCivilServantSuccessObserver)
        addSucursalViewModel.assignCivilServantFailureResponse.observe(viewLifecycleOwner, this.assignCivilServantFailureObserver)
    }

    private val validationObserver = Observer<Any?> { error ->
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private val successObserver = Observer<BranchOffice> { branchOffice ->
        Toast.makeText(context, "Sucursal agregada exitosamente.", Toast.LENGTH_SHORT).show()

        if (userIdSelected != "null") {
            addSucursalViewModel.assignCivilServant("Bearer ${getToken()}", branchOffice.id, userIdSelected)
        }

        initSucursalesFragment()
    }

    private val failureObserver = Observer<Any> { statusCode ->
        onFailResponse(statusCode, "Ocurrio un error, por favor intentá nuevamente.")
    }

    private val usersObserver = Observer<Array<DataUser>> { dataUser ->
        fullnameSpinnerArray.add("Seleccione un funcionario")

        for (user in dataUser) {
            if (user.role == "CIVIL_SERVANT" && user.refBranchOffice == null) {
                val userFuncionario = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.identificationNumber, user.phoneNumber, user.password, "", user.role, user?.refBranchOffice, user?.userDeviceToken, user.refOrganization)
                val fullname = userFuncionario.firstName + " " + userFuncionario.lastName
                listUserFuncionarios.add(userFuncionario)
                fullnameSpinnerArray.add(fullname)
            }
        }

        setSpinner()
    }

    private val assignCivilServantSuccessObserver = Observer<BranchOffice> { branchOffice ->
        Toast.makeText(context, "Funcionario asignado a sucursal exitosamente.", Toast.LENGTH_SHORT).show()
        initSucursalesFragment()
    }

    private val assignCivilServantFailureObserver = Observer<Any> { statusCode ->
        onFailResponse(statusCode, "Ocurrio un error al asignar el funcionario a la sucursal, por favor intentá nuevamente.")
    }

    private fun getUsersFuncionarios() {
        addSucursalViewModel.getUsers("Bearer/ ${getToken()}")
    }

    private fun setClickListeners() {
        binding.btnSiguiente.setOnClickListener {
            addBranchOffice()
        }
    }

    private fun addBranchOffice() {
        val name = binding.edtNombreSucursal.text.toString()
        val description = binding.edtDomicilio.text.toString()
        val refUser = userIdSelected
        val width =  if(binding.edtMt2Ancho.text.toString().isNullOrEmpty()) 0 else Integer.parseInt(binding.edtMt2Ancho.text.toString())
        val length = if(binding.edtMt2Largo.text.toString().isNullOrEmpty()) 0 else Integer.parseInt(binding.edtMt2Largo.text.toString())
        val branchOffice = BranchOffice("", "", "", name, description, refUser, 0, width, length, 0)

        addSucursalViewModel.addBranchOffice("Bearer ${getToken()}", branchOffice)
    }

    private fun initSucursalesFragment() {
        findNavController().navigate(R.id.action_addSucursalFragment_to_sucursalesSupervisorFragment)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_addSucursalFragment_to_loginFragment)
    }

    private fun onFailResponse(statusCode: Any, defaultMsg: String) {
        if(statusCode == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, defaultMsg, Toast.LENGTH_SHORT).show()
    }
}