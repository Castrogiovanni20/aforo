package com.pf.aforo.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pf.aforo.R
import com.pf.aforo.ui.home.HomeActivity
import com.pf.aforo.ui.login.LoginActivity
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen);
        nextScreen();
    }

    private fun nextScreen () {
        Timer().schedule(object : TimerTask() {
            override fun run() {
               startActivity(Intent(this@SplashActivity, HomeActivity::class.java));
            }
        }, 2000)
    }
}