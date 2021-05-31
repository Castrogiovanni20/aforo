package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.databinding.FragmentSucursalesSupervisorBinding

class SucursalesSupervisorFragment : Fragment(R.layout.fragment_sucursales_supervisor) {
    private lateinit var binding: FragmentSucursalesSupervisorBinding
    private lateinit var sucursalesSupervisorViewModel: SucursalesSupervisorViewModel
    private var arrayListSucursales = ArrayList<BranchOffice>()
    private var recyclerView : RecyclerView ?= null
    private lateinit var branchOfficeAdapter: BranchOfficeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSucursalesSupervisorBinding.bind(view)
        sucursalesSupervisorViewModel = ViewModelProvider(this).get(SucursalesSupervisorViewModel::class.java)
        setTopBar()
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
                    findNavController().navigate(R.id.action_sucursalesSupervisorFragment_to_loginFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun getBranchOffices() {
        sucursalesSupervisorViewModel.getBranchOffices("Bearer ${getToken()}")
    }

    private fun setRecyclerView() {
        recyclerView = binding.recyclerViewSucursales
        branchOfficeAdapter = BranchOfficeAdapter(arrayListSucursales)
        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = branchOfficeAdapter
    }

    private fun setObservers() {
        sucursalesSupervisorViewModel.getBranchOfficesResponse.observe(viewLifecycleOwner, this.getBranchOfficesSuccessObserver)
        sucursalesSupervisorViewModel.getBranchOfficeFailureResponse.observe(viewLifecycleOwner, this.getBranchOfficesFailureObserver)
    }

    private val getBranchOfficesSuccessObserver = Observer<ArrayList<BranchOffice>> { branchOffices ->
        arrayListSucursales.clear()
        for (branchOffice in branchOffices) {
            arrayListSucursales.add(branchOffice)
            branchOfficeAdapter.notifyDataSetChanged()
        }
    }

    private val getBranchOfficesFailureObserver = Observer<Any?> { error ->
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
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