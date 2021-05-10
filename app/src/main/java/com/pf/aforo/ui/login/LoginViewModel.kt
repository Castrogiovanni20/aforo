package com.pf.aforo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.repository.FiwareRepository

class LoginViewModel : ViewModel() {
    private var fiwareRepository = FiwareRepository()

    private var _successResponse = fiwareRepository.loginSuccessResponseLiveData
    val successResponse: MutableLiveData<String> get() = _successResponse

    private var _failureResponse = fiwareRepository.loginFailureResponseLiveData
    val failureResponse: MutableLiveData<String> get() = _failureResponse

    fun loginUser (userName: String, password: String)  {
        if (userName != "" && password != "") {
            fiwareRepository.userLogin(userName, password)
        } else {
            failureResponse.postValue("400")
        }
    }


}