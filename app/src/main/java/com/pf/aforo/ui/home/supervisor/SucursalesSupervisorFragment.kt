package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.FragmentSucursalesSupervisorBinding

class SucursalesSupervisorFragment : Fragment(R.layout.fragment_sucursales_supervisor) {
    private lateinit var binding: FragmentSucursalesSupervisorBinding
    private lateinit var sucursalesSupervisorViewModel: SucursalesSupervisorViewModel
    private var arrayListSucursales = ArrayList<BranchOffice>()
    private var arrayListFuncionarios = ArrayList<UserFuncionario>()
    private var recyclerView : RecyclerView ?= null
    private lateinit var branchOfficeAdapter1: BranchOfficeAdapter_1
    private val SUCURSAL_SIN_FUNCIONARIO: String = "Sin asignar"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSucursalesSupervisorBinding.bind(view)
        sucursalesSupervisorViewModel = ViewModelProvider(this).get(SucursalesSupervisorViewModel::class.java)
        setTopBar()
        getUsers()
        getBranchOffices()
        setRecyclerView()
        setObservers()
        setClickListeners()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemFuncionarios -> {
                    findNavController().navigate(R.id.action_sucursalesSupervisorFragment_to_usuariosSupervisorFragment)
                    true
                }
                R.id.itemSucursales -> {
                    true
                }
                R.id.itemCerrarSesion -> {
                    fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    findNavController().navigate(R.id.action_sucursalesSupervisorFragment_to_loginFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun getUsers() {
        sucursalesSupervisorViewModel.getUsers("Bearer ${getToken()}")
    }

    private fun getBranchOffices() {
        sucursalesSupervisorViewModel.getBranchOffices("Bearer ${getToken()}")
    }

    private fun setRecyclerView() {
        recyclerView = binding.recyclerViewSucursales
        branchOfficeAdapter1 = BranchOfficeAdapter_1(arrayListSucursales)
        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = branchOfficeAdapter1
    }

    private fun setObservers() {
        sucursalesSupervisorViewModel.getBranchOfficesResponse.observe(viewLifecycleOwner, this.getBranchOfficesSuccessObserver)
        sucursalesSupervisorViewModel.getBranchOfficeFailureResponse.observe(viewLifecycleOwner, this.getBranchOfficesFailureObserver)
        sucursalesSupervisorViewModel.getUsersResponse.observe(viewLifecycleOwner, this.getUsersObserver)
    }

    private val getBranchOfficesSuccessObserver = Observer<ArrayList<BranchOffice>> { branchOffices ->
        arrayListSucursales.clear()
        for (branchOffice in branchOffices) {
            arrayListSucursales.add(branchOffice)
            branchOfficeAdapter1.notifyDataSetChanged()
        }
        if(arrayListFuncionarios.isNotEmpty())
            updateBranchOfficeRefUser()
    }

    private val getBranchOfficesFailureObserver = Observer<Any?> { error ->
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private val getUsersObserver = Observer<Array<DataUser>> { dataUsers ->
        for (user in dataUsers) {
            val userFuncionario = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.phoneNumber, user.password, user.role)
            arrayListFuncionarios.add(userFuncionario)
        }
        if(arrayListSucursales.isNotEmpty())
            updateBranchOfficeRefUser()
    }

    private fun updateBranchOfficeRefUser() {
        val branchOffices = ArrayList(arrayListSucursales)
        arrayListSucursales.clear()
        for (branchOffice in branchOffices) {
            branchOffice.refUser = getFullNameOrDefaultByID(branchOffice.refUser)
            arrayListSucursales.add(branchOffice)
            branchOfficeAdapter1.notifyDataSetChanged()
        }
    }

    private fun getFullNameOrDefaultByID(id: String): String {
        if (id.equals("null")) {
            return SUCURSAL_SIN_FUNCIONARIO
        }
        var fullName = ""
        for (user in arrayListFuncionarios) {
            if (user.id == id){
                fullName = user.firstName + " " + user.lastName
                break
            }
        }
        return fullName
    }

    private fun setClickListeners(){
        binding.btnAgregarSucursal.setOnClickListener {
            findNavController().navigate(R.id.action_sucursalesSupervisorFragment_to_addSucursalFragment)
        }
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }
}