package com.pf.aforo.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.User
import com.pf.aforo.data.repository.FiwareRepository

class LoginViewModel : ViewModel() {
    private var fiwareRepository = FiwareRepository()
    var loginResponseLiveData = fiwareRepository.loginResponseLiveData

    fun login (email: String, password: String)  {
        fiwareRepository.userLogin(email, password)
    }
}