package com.pf.aforo.ui.home.funcionario

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.databinding.FragmentHomeFuncionarioBinding


class HomeFragmentFuncionario : Fragment(R.layout.fragment_home_funcionario) {
    private lateinit var binding: FragmentHomeFuncionarioBinding
    private lateinit var homeFuncionarioViewModel: HomeFuncionarioViewModel
    private val UNAUTHORIZED_CODE: String = "401"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeFuncionarioBinding.bind(view)
        homeFuncionarioViewModel = ViewModelProvider(this).get(HomeFuncionarioViewModel::class.java)
        setTopBar()
        getBranchOfficeById()
        getHistoricData()
        setObservers()
    }

    private fun getHistoricData() {
        //TODO: Implementar llamada a endpoint de historico. Crear observer que llame a setBarChart()
    }

    private fun setObservers() {
        homeFuncionarioViewModel.getBranchOfficeByIdResponse.observe(viewLifecycleOwner, this.getBranchOfficeByIdSuccessObserver)
        homeFuncionarioViewModel.getBranchOfficeByIdFailureResponse.observe(viewLifecycleOwner, this.getBranchOfficeByIdFailureObserver)
    }

    private val getBranchOfficeByIdSuccessObserver = Observer<BranchOffice> { branchOffice ->
        setHeaderUI(branchOffice)
        setPieChart()
    }

    private fun setPieChart() {
        //TODO: Implementar
    }

    private fun setBarChart() {
        //TODO: Implementar
    }

    private fun setHeaderUI(branchOffice: BranchOffice) {
        binding.titulo.setText(branchOffice.name)
        binding.domicilio.setText(branchOffice.description)
        binding.funcionario.setText(arguments?.get("userFullName").toString())
        binding.ancho.setText(branchOffice.width.toString())
        binding.alto.setText(branchOffice.length.toString())
        binding.capmax.setText(branchOffice.maxCapacity.toString())
    }

    private val getBranchOfficeByIdFailureObserver = Observer<Any?> { error ->
        if(error == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    initNotificacionesFragments()
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

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_homeFragmentFuncionario_to_loginFragment)
    }

    private fun initNotificacionesFragments() {
        findNavController().navigate(R.id.action_homeFragmentFuncionario_to_notificacionesFragment)
    }

    private fun getBranchOfficeById(){
        val refBranchOffice = arguments?.get("refBranchOffice").toString()
        homeFuncionarioViewModel.getBranchOfficeById("Bearer ${getToken()}", refBranchOffice)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

}


