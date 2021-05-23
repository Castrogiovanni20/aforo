package com.pf.aforo.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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