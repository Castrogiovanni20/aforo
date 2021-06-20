package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.FragmentHomeSupervisorBinding
import com.pf.aforo.databinding.FragmentUsuariosSupervisorBinding


class UsuariosSupervisorFragment : Fragment(R.layout.fragment_usuarios_supervisor) {
    private lateinit var binding: FragmentUsuariosSupervisorBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var currentUser: UserFuncionario
    private lateinit var currentEmailUser: String
    private var arrayListFuncionarios = ArrayList<UserFuncionario>()
    private lateinit var recyclerAdapter: RecyclerAdapter
    private var recyclerView: RecyclerView? = null
    private val CIVIL_SERVANT: String = "CIVIL_SERVANT"
    private val FUNCIONARIO: String = "FUNCIONARIO"
    private val UNAUTHORIZED_CODE: String = "401"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsuariosSupervisorBinding.bind(view)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setTopBar()
        setNavigation()
        setOnClickListeners()
        getUsers()
        setUpRecyclerView()
        getEmailUser()
        setObservers()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    findNavController().navigate(R.id.action_usuariosSupervisorFragment_to_notificacionesSupervisorFragment)
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

    private fun setNavigation(){
        binding.bottomNavigation.selectedItemId = R.id.itemFuncionarios

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemHome -> {
                    findNavController().navigate(R.id.action_usuariosSupervisorFragment_to_homeFragmentSupervisor)
                    true
                }
                R.id.itemSucursales -> {
                    findNavController().navigate(R.id.action_usuariosSupervisorFragment_to_sucursalesSupervisorFragment)
                    true
                }
                R.id.itemPerfil -> {
                    findNavController().navigate(R.id.action_usuariosSupervisorFragment_to_editProfileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_usuariosSupervisorFragment_to_sucursalesSupervisorFragment)
    }

    private fun setOnClickListeners() {
        binding.btnAgregarFuncionario.setOnClickListener {
            findNavController().navigate(R.id.action_usuariosSupervisorFragment_to_addUserFragment)
        }
    }

    private fun setUpRecyclerView(){
        recyclerView = view?.findViewById(R.id.recyclerView);
        recyclerAdapter = RecyclerAdapter(arrayListFuncionarios)
        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = recyclerAdapter
    }

    private fun setObservers () {
        homeViewModel.getUsersResponse.observe(viewLifecycleOwner, getUsersSuccessObserver)
        homeViewModel.getUsersFailureResponse.observe(viewLifecycleOwner, getUsersFailureObserver)
    }

    private val getUsersSuccessObserver = Observer<Any?> { users ->
        getCurrentEmailUser()
        var usersList : Array<DataUser> = users as Array<DataUser>
        arrayListFuncionarios.clear()

        for (user in usersList) {
            if(user.role == CIVIL_SERVANT)
                user.role = FUNCIONARIO
            if (user.email != currentEmailUser) {
                val userFuncionario = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.identificationNumber, user.phoneNumber, user.password, "", user.role)
                arrayListFuncionarios.add(userFuncionario)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                currentUser = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.identificationNumber, user.phoneNumber, user.password, "", user.role)
            }
        }
    }

    private val getUsersFailureObserver = Observer<Any?> { error ->
        if(error == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
        Log.d("FailureObserver", error.toString())
    }

    private fun getUsers() {
        val token = getToken()
        homeViewModel.getUsers("Bearer: $token")
    }

    private fun getEmailUser() {
        currentEmailUser = arguments?.getString("Email").toString()
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun getCurrentEmailUser() {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        currentEmailUser = sharedPref?.getString("Email", "0").toString()
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_usuariosSupervisorFragment_to_loginFragment)
    }

}