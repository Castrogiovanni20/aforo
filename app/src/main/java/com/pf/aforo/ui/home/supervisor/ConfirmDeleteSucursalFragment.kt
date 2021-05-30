package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentConfirmDeleteSucursalBinding

class ConfirmDeleteSucursalFragment : Fragment(R.layout.fragment_confirm_delete_sucursal) {
    private lateinit var binding: FragmentConfirmDeleteSucursalBinding
    private lateinit var editSucursalViewModel: EditSucursalViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmDeleteSucursalBinding.bind(view)
        editSucursalViewModel = ViewModelProvider(this).get(EditSucursalViewModel::class.java)
        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners() {
        binding.btnOK.setOnClickListener {
            deleteBranchOffice();
        }
    }

    private fun setObservers(){
        editSucursalViewModel.deleteBranchOfficeSuccessResponse.observe(viewLifecycleOwner, successObserver)
        editSucursalViewModel.deleteBranchOfficeFailureResponse.observe(viewLifecycleOwner, failureObserver)
    }

    private val successObserver = Observer<Any> {
        Toast.makeText(context, "Sucursal eliminada exitosamente.", Toast.LENGTH_SHORT).show()
        initSucursalesScreen()
    }

    private val failureObserver = Observer<Any> { statusCode ->
        Toast.makeText(context, "Ocurrio un error, por favor intentá nuevamente.", Toast.LENGTH_SHORT).show()
    }

    private fun deleteBranchOffice() {
        val bundle = this.arguments
        val idBranchOffice = bundle?.getString("idBranchOffice")
        if (idBranchOffice != null) {
            editSucursalViewModel.deleteBranchOffice("Bearer ${getToken()}", idBranchOffice)
        }
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun initSucursalesScreen() {
        findNavController().navigate(R.id.action_confirmDeleteSucursalFragment_to_sucursalesSupervisorFragment)
    }

}