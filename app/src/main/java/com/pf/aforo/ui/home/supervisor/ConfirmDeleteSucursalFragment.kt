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
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.databinding.FragmentConfirmDeleteSucursalBinding

class ConfirmDeleteSucursalFragment : Fragment(R.layout.fragment_confirm_delete_sucursal) {
    private lateinit var binding: FragmentConfirmDeleteSucursalBinding
    private lateinit var editSucursalViewModel: EditSucursalViewModel
    private lateinit var branchOfficeId: String
    private var branchOfficeRefUser: String? = ""
    private val UNAUTHORIZED_CODE: String = "401"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmDeleteSucursalBinding.bind(view)
        editSucursalViewModel = ViewModelProvider(this).get(EditSucursalViewModel::class.java)
        setOnClickListeners()
        setObservers()
        setTopBar()
        getBranchOffice()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    findNavController().navigate(R.id.action_confirmDeleteSucursalFragment_to_notificacionesSupervisorFragment)
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
            onBackPressed();
        }
    }

    private fun getBranchOffice() {
        val bundle = this.arguments
        val entityId = bundle?.getString("entityId")

        if (entityId != null) {
            editSucursalViewModel.getBranchOffice("Bearer ${getToken()}", entityId)
        }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_confirmDeleteSucursalFragment_to_sucursalesSupervisorFragment)
    }

    private fun setOnClickListeners() {
        binding.btnOK.setOnClickListener {
            deleteBranchOffice();
        }
    }

    private fun setObservers(){
        editSucursalViewModel.deleteBranchOfficeSuccessResponse.observe(viewLifecycleOwner, successObserver)
        editSucursalViewModel.deleteBranchOfficeFailureResponse.observe(viewLifecycleOwner, failureObserver)
        editSucursalViewModel.branchOffice.observe(viewLifecycleOwner, branchOfficeObserver)
    }

    private val successObserver = Observer<Any> {
        Toast.makeText(context, "Sucursal eliminada exitosamente.", Toast.LENGTH_SHORT).show()
        initSucursalesScreen()
    }

    private val failureObserver = Observer<Any> { statusCode ->
        if(statusCode == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }

    private val branchOfficeObserver = Observer<BranchOffice> { branchOffice ->
        branchOfficeId = branchOffice.id
        branchOfficeRefUser = branchOffice?.refUser
    }

    private fun deleteBranchOffice() {
        if (branchOfficeId != null && branchOfficeRefUser != null) {
            editSucursalViewModel.removeCivilServant("Bearer ${getToken()}", branchOfficeId, branchOfficeRefUser)
            editSucursalViewModel.deleteBranchOffice("Bearer ${getToken()}", branchOfficeId)
        } else if (branchOfficeId != null) {
            editSucursalViewModel.deleteBranchOffice("Bearer ${getToken()}", branchOfficeId)
        }
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun initSucursalesScreen() {
        findNavController().navigate(R.id.action_confirmDeleteSucursalFragment_to_sucursalesSupervisorFragment)
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_confirmDeleteSucursalFragment_to_loginFragment)
    }

}