package com.pf.aforo.ui.register

import android.util.Log
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

   // val failureValidation: MutableLiveData<String> get()

    fun registerUser(user: User){
        if ((user.getFullName().length < 5) || (user.getFullName().length > 15)) {

        }
        fiwareRepository.register(user)
    }
}