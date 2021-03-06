package com.pf.aforo.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.pf.aforo.R
import com.pf.aforo.data.model.*
import com.pf.aforo.databinding.FragmentLoginBinding
import com.google.firebase.messaging.FirebaseMessaging



class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userId: String
    private lateinit var token: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentLoginBinding.bind(view)
        (activity as AppCompatActivity?)!!.actionBar?.hide()
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java);
        setObservers()
        setClickListeners()
        requireActivity().onBackPressedDispatcher.addCallback(this) { requireActivity().moveTaskToBack(true) }
        getToken()

        FirebaseMessaging.getInstance().subscribeToTopic("Subscriptions")
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            val deviceToken = instanceIdResult.token
            Log.d("Token", "Refreshed token: $deviceToken")
        }
    }

    override fun onResume() {
        super.onResume()
        clearUI()
    }

    private fun clearUI() {
        binding.edtEmail.text?.clear()
        binding.edtEmail.text?.clear()
    }

    private fun getToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("MainActivity", "error al obtener el token", task.exception)
                return@OnCompleteListener
            }
            token = task.result!!.token
            Log.d("MainActivity", token)

            val sharedPreferences = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putString("DeviceToken", token)
            editor?.apply()
        })
    }

    private fun setObservers () {
        loginViewModel.loginDataResponseLiveData.observe(viewLifecycleOwner, loginObserver)
        loginViewModel.failureResponse.observe(viewLifecycleOwner, failureObserver)
        loginViewModel.validationError.observe(viewLifecycleOwner, validationObserver)

        loginViewModel.userResponseLiveData.observe(viewLifecycleOwner, userObserver)
        loginViewModel.getUserFailureResponse.observe(viewLifecycleOwner, userFailureObserver)

        loginViewModel.startProgressBar.observe(viewLifecycleOwner, this.startProgressBarObserver)
        loginViewModel.stopProgressBar.observe(viewLifecycleOwner, this.stopProgressBarObserver)
    }

    private fun setClickListeners () {
        binding.btnSend.setOnClickListener{
            login()
        }

        binding.txtCreateAccount.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun login () {
        var userName = binding.edtEmail.text.toString()
        var password = binding.edtPass.text.toString()
        val userLogin = UserLogin(userName, password)

        loginViewModel.startProgressBar()
        loginViewModel.loginUser(userLogin)
    }

    private val loginObserver = Observer<Data> { data ->
        userId = data.userId
        token = data.token

        loginViewModel.getUser("Bearer $token", userId)
        setSharedPreferences(token)

    }

    private fun updateUser(user: DataUser) {
        var firebaseMess = MyFirebaseMessaging()
//        var tokenMessage = firebaseMess.getToken()
        val sharedPreferences = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        var tokenMessage = sharedPreferences?.getString("DeviceToken", null)

        var userF = UserFuncionario(
            user.id,
            user.firstName,
            user.lastName,
            user.email,
            user.identificationNumber,
            user.phoneNumber,
            user.password,
            user.password,
            user.role,
            user.refBranchOffice,
            tokenMessage,
            user.refOrganization
        )
        loginViewModel.updateUser("Bearer $token", userF.id, userF)
    }

    private val userObserver = Observer<DataUser> { user ->
        loginViewModel.stopProgressBar()
        updateUser(user)

        when (user.role) {
            "SUPERVISOR" -> initFragmentHomeSupervisor()
            "CIVIL_SERVANT" -> initFragmentHomeFuncionario(user)
        }
    }

    private val userFailureObserver = Observer<String> {
        loginViewModel.stopProgressBar()
        Toast.makeText(context, "Algo salio mal", Toast.LENGTH_SHORT).show()
    }

    private val failureObserver = Observer<Any?> { statusCode ->
        loginViewModel.stopProgressBar()
        when (statusCode) {
            "500", "401" -> {
                Toast.makeText(context, "Usuario y/o contrase??a incorrecto.", Toast.LENGTH_SHORT)
                    .show()
            }
            "404" -> {
                Toast.makeText(
                    context,
                    "Estamos teniendo problemas con nuestro servidor. Por favor intent?? loguearte m??s tarde.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            "400" -> {
                Toast.makeText(
                    context,
                    "Por favor, complet?? el usuario y/o contrase??a.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private val validationObserver = Observer<Any?> { error ->
        loginViewModel.stopProgressBar()
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private val startProgressBarObserver = Observer<Boolean> {
        binding.loadingSpinner.visibility = View.VISIBLE
    }

    private val stopProgressBarObserver = Observer<Boolean> {
        binding.loadingSpinner.visibility = View.GONE
    }

    private fun initFragmentHomeFuncionario(user: DataUser) {
        userId = user.id
        val refBranchOffice = user.refBranchOffice
        if(refBranchOffice != null && !refBranchOffice.equals("null")){
            val userFullName = user.firstName + " " + user.lastName
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragmentFuncionario(
                    userFullName,
                    refBranchOffice
                )
            )
        }
        else
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragmentFuncionarioSinSucursal())
    }

    private fun initFragmentHomeSupervisor() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragmentSupervisor)
    }

    private fun setSharedPreferences(token: String) {
        val sharedPreferences = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("Token", token)
        editor?.putString("Email", binding.edtEmail.text.toString())
        editor?.putString("UserID", userId)
        editor?.apply()
    }

}