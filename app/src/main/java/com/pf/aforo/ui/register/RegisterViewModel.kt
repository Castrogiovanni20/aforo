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

    var validationError = MutableLiveData<String>()

    fun registerUser(user: User){
        Log.d("PhoneNumber", user.getPhoneNumber().toString())
        when {
            user.getFullName().length < 5 || user.getFullName().length > 15 -> {
                validationError.value = "FullName"
            }
            user.getEmail().length < 10 -> {
                validationError.value = "Email"
            }
            user.getPhoneNumber() < 8 -> {
                validationError.value = "Phone"
            }
            user.getOrganization().length < 2 -> {
                validationError.value = "Organization"
            }
            user.getPassword().length < 8 -> {
                validationError.value = "Password"
            }
            else -> {
                fiwareRepository.register(user)
            }
        }
    }
}