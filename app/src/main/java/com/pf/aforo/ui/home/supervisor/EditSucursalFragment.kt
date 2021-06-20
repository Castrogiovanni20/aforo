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
    private var listAllCivilServants = ArrayList<UserFuncionario>()
    private var listCivilServantsAvailables = ArrayList<UserFuncionario>()
    private var fullnameSpinnerArray = ArrayList<String>()
    private var userIdSelected: String? = ""
    private val UNAUTHORIZED_CODE: String = "401"
    private val SUCURSAL_SIN_FUNCIONARIO: String = "Sin asignar"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditSucursalBinding.bind(view)
        editSucursalViewModel = ViewModelProvider(this).get(EditSucursalViewModel::class.java)
        setTopBar()
        getBranchOffice()
        setObservers()
        setClickListeners()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    findNavController().navigate(R.id.action_editSucursalFragment_to_notificacionesSupervisorFragment)
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

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_editSucursalFragment_to_sucursalesSupervisorFragment)
    }

    private fun setUI() {
        binding.edtNombreSucursal.setText(branchOffice.name)
        binding.edtDomicilio.setText(branchOffice.description)
        binding.edtMt2Ancho.setText(branchOffice.width.toString())
        binding.edtMt2Largo.setText(branchOffice.length.toString())
        setCivilServantFullName()
        setSpinner()
    }

    private fun setCivilServantFullName() {
        var refUser: String = SUCURSAL_SIN_FUNCIONARIO

        if (branchOffice.refUser != null) {
            for (user in listAllCivilServants) {
                if (user.id == branchOffice.refUser){
                    refUser = user.firstName + " " + user.lastName
                }
            }
        }

        binding.textFuncionarioAsignado.text = "Funcionario asignado: " + refUser
    }

    private fun setSpinner() {
        val spinner = binding.spinnerFuncionario

        if (listCivilServantsAvailables.isNotEmpty()) {
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
                    userIdSelected = listCivilServantsAvailables[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        } else {
            binding.spinnerFuncionario.visibility = View.GONE
            userIdSelected = null
        }

    }

    private fun setObservers() {
        editSucursalViewModel.branchOffice.observe(viewLifecycleOwner, branchOfficeObserver)
        editSucursalViewModel.validationError.observe(viewLifecycleOwner, this.validationObserver)
        editSucursalViewModel.updateBranchOfficeSuccessResponse.observe(viewLifecycleOwner, successObserver)
        editSucursalViewModel.updateBranchOfficeFailureResponse.observe(viewLifecycleOwner, failureObserver)
        editSucursalViewModel.getUsersSuccessResponse.observe(viewLifecycleOwner, usersObserver)
    }

    private fun setClickListeners() {
        binding.btnSiguiente.setOnClickListener {
            editBranchOffice()
        }

        binding.textViewEliminar.setOnClickListener {
            val refUser = branchOffice?.refUser
            initConfirmDeleteSucursalScreen(branchOffice.id, refUser)
        }
    }

    private fun editBranchOffice() {
        val id = branchOffice.id
        val name = binding.edtNombreSucursal.text.toString()
        val refOrganization = binding.edtNombreSucursal.text.toString()
        val description = binding.edtDomicilio.text.toString()
        val width = if(binding.edtMt2Ancho.text.toString().isNullOrEmpty()) 0 else Integer.parseInt(binding.edtMt2Ancho.text.toString())
        val length = if(binding.edtMt2Largo.text.toString().isNullOrEmpty()) 0 else Integer.parseInt(binding.edtMt2Largo.text.toString())
        val refUser: String? = userIdSelected

        val newBranchOffice = BranchOffice("", id, refOrganization, name, description, refUser, 0, width, length, 0)
        editSucursalViewModel.removeCivilServant("Bearer ${getToken()}", newBranchOffice.id, branchOffice.refUser)
        editSucursalViewModel.updateBranchOffice("Bearer ${getToken()}", newBranchOffice.id, newBranchOffice)
        editSucursalViewModel.assignCivilServant("Bearer ${getToken()}", newBranchOffice.id, newBranchOffice?.refUser)
    }

    private val branchOfficeObserver = Observer<BranchOffice> { it ->
        branchOffice = it
        getUsersFuncionarios()
    }

    private val validationObserver = Observer<Any?> { error ->
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
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

    private val usersObserver = Observer<Array<DataUser>> { arrayUsers ->
        setArrayAllCivilServants(arrayUsers)
        setArrayCivilServantsAvailables(arrayUsers)
        setUI()
    }

    private fun setArrayCivilServantsAvailables(arrayUsers: Array<DataUser>) {
        for (user in arrayUsers) {
            if (user.role == "CIVIL_SERVANT" && user.refBranchOffice == null) {
                val civilServant = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.identificationNumber, user.phoneNumber, user.password, "",user.role, user.refBranchOffice)
                val fullname = civilServant.firstName + " " + civilServant.lastName
                listCivilServantsAvailables.add(civilServant)
                fullnameSpinnerArray.add(fullname)
            }

            if (user.id == branchOffice.refUser) {
                currentUser = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.identificationNumber, user.phoneNumber, user.password, "", user.role, null)
            }
        }
    }

    private fun setArrayAllCivilServants(arrayUsers: Array<DataUser>) {
        for (user in arrayUsers) {
            if (user.role == "CIVIL_SERVANT") {
                val civilServant = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.identificationNumber, user.phoneNumber, user.password, "", user.role, user.refBranchOffice)
                val fullname = civilServant.firstName + " " + civilServant.lastName
                listAllCivilServants.add(civilServant)
            }
        }
    }


    private fun getUsersFuncionarios() {
        editSucursalViewModel.getUsers("Bearer ${getToken()}")
    }

    private fun getBranchOffice() {
        val branchOfficeId = arguments?.getString("BranchOfficeId")!!
        if (branchOfficeId != null) {
            editSucursalViewModel.getBranchOffice("Bearer ${getToken()}", branchOfficeId)
        }
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun initSucursalesSupervisorScreen() {
        findNavController().navigate(R.id.action_editSucursalFragment_to_sucursalesSupervisorFragment)
    }

    private fun initConfirmDeleteSucursalScreen(entityId: String, refUser: String?) {
        val bundle = Bundle()
        bundle.putString("entityId", branchOffice.id)
        findNavController().navigate(R.id.action_editSucursalFragment_to_confirmDeleteSucursalFragment, bundle)
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_editSucursalFragment_to_loginFragment)
    }
}