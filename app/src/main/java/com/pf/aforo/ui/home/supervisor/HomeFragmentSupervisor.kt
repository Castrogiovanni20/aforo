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
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.Data
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.databinding.FragmentHomeSupervisorBinding
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException


class HomeFragmentSupervisor : Fragment(R.layout.fragment_home_supervisor) {
    private lateinit var mSocket: Socket
    private lateinit var binding: FragmentHomeSupervisorBinding
    private lateinit var homeViewModel: HomeViewModel
    private var arrayListSucursales = ArrayList<BranchOffice>()
    private var arrayListFuncionarios = ArrayList<UserFuncionario>()
    private var recyclerView : RecyclerView?= null
    private lateinit var branchOfficeAdapter2: BranchOfficeAdapter_2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeSupervisorBinding.bind(view)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setTopBar()
        getUsers()
        getBranchOffices()
        setRecyclerView()
        setObservers()

        mSocket = connectSocket()
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket.on("CONTEXT_CHANGE", onContextChange)
    }

    private fun connectSocket() : Socket {
        try {
            mSocket = IO.socket("http://46.17.108.79:5000");
            mSocket.connect()
        } catch (e: URISyntaxException) {
            Log.d("SocketError: ", e.toString())
        }

        return mSocket;
    }

    private val onConnect = Emitter.Listener { args ->
        Log.d("Socket", "El ID es: " + mSocket.id())
    }

    private val onDisconnect = Emitter.Listener { args ->
        Log.d("Socket", "Desconectado")
    }

    private val onContextChange = Emitter.Listener { args ->
        Log.d("Socket", "Hubo un cambio")

        requireActivity().runOnUiThread(Runnable {
            Log.d("Socket", "Entro en el evento")
        })
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemFuncionarios -> {
                    initFuncionariosFragments()
                    true
                }
                R.id.itemSucursales -> {
                    initSucursalesFragment()
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

    private fun getUsers() {
        homeViewModel.getUsers("Bearer ${getToken()}")
    }

    private fun getBranchOffices() {
        homeViewModel.getBranchOffices("Bearer ${getToken()}")
    }

    private fun setRecyclerView() {
        recyclerView = binding.recyclerViewSucursales
        branchOfficeAdapter2 = BranchOfficeAdapter_2(arrayListSucursales)
        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = branchOfficeAdapter2
    }

    private fun setObservers() {
        homeViewModel.getBranchOfficesResponse.observe(viewLifecycleOwner, this.getBranchOfficesSuccessObserver)
        homeViewModel.getBranchOfficesFailureResponse.observe(viewLifecycleOwner, this.getBranchOfficesFailureObserver)
        homeViewModel.getUsersResponse.observe(viewLifecycleOwner, this.getUsersObserver)
    }

    private val getBranchOfficesSuccessObserver = Observer<ArrayList<BranchOffice>> { branchOffices ->
        arrayListSucursales.clear()
        for (branchOffice in branchOffices) {
            branchOffice.refUser = getFullNameByID(branchOffice.refUser)
            arrayListSucursales.add(branchOffice)
            branchOfficeAdapter2.notifyDataSetChanged()
        }
    }

    private val getBranchOfficesFailureObserver = Observer<Any?> { error ->
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private val getUsersObserver = Observer<Array<DataUser>> { dataUsers ->
        for (user in dataUsers) {
            val userFuncionario = UserFuncionario(user.id, user.firstName, user.lastName, user.email, user.phoneNumber, user.password, user.role)
            arrayListFuncionarios.add(userFuncionario)
        }
    }

    private fun getFullNameByID(id: String): String {
        var fullName = ""
        for (user in arrayListFuncionarios) {
            if (user.id == id) {
                fullName = user.firstName + " " + user.lastName
            }
        }

        return fullName
    }

    private fun initFuncionariosFragments() {
        findNavController().navigate(R.id.action_homeFragmentSupervisor_to_usuariosSupervisorFragment)
    }

    private fun initSucursalesFragment () {
        findNavController().navigate(R.id.action_homeFragmentSupervisor_to_sucursalesSupervisorFragment)
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_homeFragmentSupervisor_to_loginFragment)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

}