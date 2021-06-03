package com.pf.aforo.ui.home.supervisor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentHomeSupervisorBinding
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException


class HomeFragmentSupervisor : Fragment(R.layout.fragment_home_supervisor) {

    private lateinit var mSocket: Socket
    private lateinit var binding: FragmentHomeSupervisorBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeSupervisorBinding.bind(view)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setTopBar()
        mSocket = connectSocket()
        mSocket.on(Socket.EVENT_CONNECT, onNewMessage);
    }

    private fun connectSocket() : Socket {
        try {
            mSocket = IO.socket("http://192.168.0.16:3000");
            mSocket.connect()
        } catch (e: URISyntaxException) {
            Log.d("SocketError: ", e.toString())
        }

        return mSocket;
    }

    private val onNewMessage = Emitter.Listener { args ->
        Log.d("El id es: ", mSocket.id())
        /*
        requireActivity().runOnUiThread(Runnable {
            Log.d("Socket", "Entro en el evento")
            val data = args[0] as JSONObject
            val username: String
            val message: String
            try {
                username = data.getString("username")
                message = data.getString("message")
            } catch (e: JSONException) {
                return@Runnable
            }
        })*/
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

    private fun initFuncionariosFragments() {
        findNavController().navigate(R.id.action_homeFragmentSupervisor_to_usuariosSupervisorFragment)
    }

    private fun initSucursalesFragment () {
        findNavController().navigate(R.id.action_homeFragmentSupervisor_to_sucursalesSupervisorFragment)
    }

    private fun initLoginFragment() {
        findNavController().navigate(R.id.action_homeFragmentSupervisor_to_loginFragment)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

}