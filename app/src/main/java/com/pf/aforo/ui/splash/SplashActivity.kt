package com.pf.aforo.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pf.aforo.R
import com.pf.aforo.databinding.ActivitySplashScreenBinding
import com.pf.aforo.ui.home.HomeActivity
import com.pf.aforo.ui.login.LoginActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUI();
        setObserver();
    }

    private fun setUI () {
        binding = ActivitySplashScreenBinding.inflate(layoutInflater);
        setContentView(binding.root);
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java);
    }

    private fun setObserver () {
        splashViewModel.startTimer()
        splashViewModel.flag.observe(this, Observer {
            if (it) {
                initLoginScreen()
            }
        })
    }

    private fun initLoginScreen () {
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java));
    }

}