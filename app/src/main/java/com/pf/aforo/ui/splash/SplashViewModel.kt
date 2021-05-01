package com.pf.aforo.ui.splash

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.ui.login.LoginActivity
import java.util.*

class SplashViewModel : ViewModel(){

    val flag = MutableLiveData<Boolean>()

    fun startTimer () {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                flag.postValue(true);
            }
        }, 2000)
    }
}