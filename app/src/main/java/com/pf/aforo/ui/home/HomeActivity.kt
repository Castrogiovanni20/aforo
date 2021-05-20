package com.pf.aforo.ui.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.R
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.ActivityRecyclerFuncionariosBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerFuncionariosBinding
    private lateinit var homeViewModel: HomeViewModel
    private var arrayListFuncionarios = ArrayList<UserFuncionario>()
    private lateinit var recyclerAdapter: RecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_funcionarios)
        setUI()
        setUpRecyclerView()
        getUsers()
        setObservers()
        setClickListeners()
    }

    private fun setUI () {
        binding = ActivityRecyclerFuncionariosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    fun setUpRecyclerView(){
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerAdapter = RecyclerAdapter(arrayListFuncionarios)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = recyclerAdapter
    }


    private fun setObservers () {
        homeViewModel.usersResponse.observe(this, usersObserver)
        homeViewModel.failureResponse.observe(this, failureObserver)
    }


    private val usersObserver = Observer<Any?> { users ->
        var usersList : Array<DataUser> = users as Array<DataUser>

        for (user in usersList) {
            var userFuncionario = UserFuncionario(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getRefOrganization(), user.getPassword(), user.getRole())
            arrayListFuncionarios.add(userFuncionario)
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    private val failureObserver = Observer<Any?> { error ->
        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
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
        startActivity(Intent(this@HomeActivity, AddUserActivity::class.java))
    }

    private fun getToken(): String {
        val sharedPref = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref.getString("Token", "0").toString()
    }
}
