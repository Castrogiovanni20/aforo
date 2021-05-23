package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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


class HomeFragmentSupervisor : Fragment(R.layout.fragment_home_supervisor) {
    private lateinit var binding: FragmentHomeSupervisorBinding
    private lateinit var homeViewModel: HomeViewModel
    private var arrayListFuncionarios = ArrayList<UserFuncionario>()
    private lateinit var recyclerAdapter: RecyclerAdapter
    private var recyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        binding = FragmentHomeSupervisorBinding.bind(view)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setUpRecyclerView()
        getUsers()
        setObservers()
        setClickListeners()
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
        var usersList : Array<DataUser> = users as Array<DataUser>
        arrayListFuncionarios.clear()

        for (user in usersList) {
            val userFuncionario = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.phoneNumber, user.password, user.role)
            arrayListFuncionarios.add(userFuncionario)
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    private val getUsersFailureObserver = Observer<Any?> { error ->
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
        Log.d("FailureObserver", error.toString())
    }

    private fun setClickListeners () {
        binding.btnAgregarFuncionario.setOnClickListener {
            initAddUserActivity()
        }
    }

    private fun getUsers() {
        val token = getToken()
        homeViewModel.getUsers("Bearer: $token")
    }

    private fun initAddUserActivity () {
        findNavController().navigate(R.id.action_homeFragmentSupervisor_to_addUserFragment)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }
}