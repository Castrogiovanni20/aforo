package com.pf.aforo.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.User
import com.pf.aforo.data.repository.FiwareRepository

class LoginViewModel : ViewModel() {
    var loginResponseLiveData = MutableLiveData<String>();

    fun login (email: String, password: String) {
        if (email == "pepe" && password == "123"){
            loginResponseLiveData = FiwareRepository().userLogin(email, password);
        }
    }
}