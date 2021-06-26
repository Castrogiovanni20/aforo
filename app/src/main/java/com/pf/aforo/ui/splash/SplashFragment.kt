package com.pf.aforo.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.databinding.FragmentSplashBinding


class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var splashViewModel: SplashViewModel
    private val UNAUTHORIZED_CODE: String = "401"
    private val USER_NOT_FOUND_CODE: String = "404"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentSplashBinding.bind(view)
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java);
        setObservers()
    }


    private fun setObservers() {
        splashViewModel.startTimer()
        splashViewModel.flag.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigate()
            }
        })
        splashViewModel.userResponseLiveData.observe(viewLifecycleOwner, userObserver)
        splashViewModel.getUserFailureResponse.observe(viewLifecycleOwner, userFailureObserver)
    }

    private fun navigate() {
        val sharedPreferences = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("Token", null)
        val userId = sharedPreferences?.getString("UserID", null)

        if(token != null && userId != null){
            splashViewModel.getUser("Bearer $token", userId)
        }
        else
            initLoginFragment()
    }

    private val userObserver = Observer<DataUser> { user ->
        when (user.role) {
            "SUPERVISOR" -> initFragmentHomeSupervisor()
            "CIVIL_SERVANT" -> initFragmentHomeFuncionario(user)
        }
    }

    private val userFailureObserver = Observer<Any?> { error ->
        var mensaje = "Ha ocurrido un error." + error.toString()
        if (error == UNAUTHORIZED_CODE)
            mensaje =  "La sesión ha expirado."
        else if (error == USER_NOT_FOUND_CODE)
            mensaje = "Usuario no encontrado."

        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        clearSharedPreferences()
        initLoginFragment()
    }

    private fun initLoginFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }

    private fun initFragmentHomeSupervisor() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragmentSupervisor)
    }

    private fun initFragmentHomeFuncionario(user: DataUser) {
        val refBranchOffice = user.refBranchOffice
        if(refBranchOffice != null && !refBranchOffice.equals("null")){
            val userFullName = user.firstName + " " + user.lastName
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragmentFuncionario(userFullName, refBranchOffice))
        }
        else
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragmentFuncionarioSinSucursal())
    }

    private fun clearSharedPreferences() {
        context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.edit()?.clear()?.commit()
    }

}