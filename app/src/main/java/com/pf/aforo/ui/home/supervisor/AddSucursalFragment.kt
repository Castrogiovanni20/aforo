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
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.databinding.FragmentAddSucursalBinding

class AddSucursalFragment : Fragment(R.layout.fragment_add_sucursal) {
    private lateinit var binding: FragmentAddSucursalBinding
    private lateinit var addSucursalViewModel: AddSucursalViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddSucursalBinding.bind(view)
        addSucursalViewModel = ViewModelProvider(this).get(AddSucursalViewModel::class.java)
        setTopBar()
        setObservers()
        setClickListeners()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemFuncionarios -> {
                    findNavController().navigate(R.id.action_addSucursalFragment_to_homeFragmentSupervisor)
                    true
                }
                R.id.itemSucursales -> {
                    findNavController().navigate(R.id.action_addSucursalFragment_to_sucursalesSupervisorFragment)
                    true
                }
                R.id.itemCerrarSesion -> {
                    findNavController().navigate(R.id.action_addSucursalFragment_to_loginFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setObservers() {
        addSucursalViewModel.addBranchOfficeSuccessResponse.observe(viewLifecycleOwner, this.successObserver)
        addSucursalViewModel.addBranchOfficeFailureResponse.observe(viewLifecycleOwner, this.failureObserver)
    }

    private val successObserver = Observer<BranchOffice> { branchOffice ->
        Toast.makeText(context, "Sucursal agregada exitosamente.", Toast.LENGTH_SHORT).show()
        initSucursalesFragment()
        val branchOfficeId = branchOffice.id
    }

    private val failureObserver = Observer<Any> { statusCode ->
        Toast.makeText(context, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }

    private fun setClickListeners() {
        binding.btnSiguiente.setOnClickListener {
            addBranchOffice()
        }
    }

    private fun addBranchOffice() {
        val refOrganization = binding.edtOrganizacion.text.toString()
        val name = binding.edtNombreSucursal.text.toString()
        val description = binding.edtDomicilio.text.toString()
        val refUser = "test"
        val width =  Integer.parseInt(binding.edtMt2Ancho.text.toString())
        val length = Integer.parseInt(binding.edtMt2Alto.text.toString())
        val branchOffice = BranchOffice("", "", refOrganization, name, description, refUser, 0, width, length, 0)

        addSucursalViewModel.addBranchOffice("Bearer ${getToken()}", branchOffice)
    }

    private fun initSucursalesFragment() {
        findNavController().navigate(R.id.action_addSucursalFragment_to_sucursalesSupervisorFragment)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }
}