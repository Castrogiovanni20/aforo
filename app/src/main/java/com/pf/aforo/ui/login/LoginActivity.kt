package com.pf.aforo.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pf.aforo.R
import com.pf.aforo.data.model.User
import com.pf.aforo.data.repository.FiwareRepository
import com.pf.aforo.databinding.ActivityLoginBinding
import com.pf.aforo.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding;
    private lateinit var loginViewModel: LoginViewModel;
    private var user: User?= null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUI();
        setObservers();
        login();
    }

    private fun setUI (){
        binding = ActivityLoginBinding.inflate(layoutInflater);
        setContentView(binding.root);
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java);

    }

    private fun setObservers () {
        loginViewModel.loginResponseLiveData.observe(this, Observer {
            Log.d("TEST", "HUBO UN CAMBIO");
            if (it == "test") {
                initHomeScreen();
            }
        })
    }

    private fun login () {
        loginViewModel.login("pepe", "123")
    }

    private fun initHomeScreen () {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java));
    }
}