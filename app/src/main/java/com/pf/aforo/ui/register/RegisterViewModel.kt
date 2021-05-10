package com.pf.aforo.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.User
import com.pf.aforo.data.repository.FiwareRepository
import com.pf.aforo.data.repository.user

class RegisterViewModel : ViewModel () {
    private var fiwareRepository = FiwareRepository()

    private var _successResponse = fiwareRepository.registerSuccessResponseLiveData
    val successResponse: MutableLiveData<String> get() = _successResponse

    private var _failureResponse = fiwareRepository.registerFailureResponseLiveData
    val failureResponse: MutableLiveData<String> get() = _failureResponse

    fun registerUser(user: User){
        var user = User("Ramiro Castro", "ramiroaquino123@gmail.com", 12345, "ORT", "12345678")
        FiwareRepository().register(user)
    }
}