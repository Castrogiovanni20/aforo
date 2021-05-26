package com.pf.aforo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.data.repository.AuthenticationRepository

//Estas son las variables observables + validaciones ac+a para sacar mayor cantidad de código del activitie:
class LoginViewModel : ViewModel() {
    private var authenticationRepository = AuthenticationRepository() //ver repository, para obtener acceso a login, registro

    private var _successResponse = authenticationRepository.loginSuccessResponseLiveData
    val successResponse: MutableLiveData<String> get() = _successResponse

    private var _failureResponse = authenticationRepository.loginFailureResponseLiveData
    val failureResponse: MutableLiveData<String> get() = _failureResponse

    var validationError = MutableLiveData<String>()
    var isUserValid : Boolean = false

    fun loginUser (user: UserLogin)  {
        validateUser(user)
        if (isUserValid) authenticationRepository.login(user)
    }

    private fun validateUser(user: UserLogin) {
        when {
            !user.isUserNameValid() -> validationError.value = "Debe ingresar su email."
            !user.isEmailValid() -> validationError.value = "Debe ingresar un formato de email valido."
            !user.isPasswordValid() -> validationError.value = "Debe ingresar su contraseña."
            else -> {
                isUserValid = true
            }
        }
    }
}