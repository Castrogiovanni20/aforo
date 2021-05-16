package com.pf.aforo.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.UserRegister
import com.pf.aforo.data.repository.FiwareRepository

class RegisterViewModel : ViewModel () {
    private var fiwareRepository = FiwareRepository()

    private var _successResponse = fiwareRepository.registerSuccessResponseLiveData
    val successResponse: MutableLiveData<String> get() = _successResponse

    private var _failureResponse = fiwareRepository.registerFailureResponseLiveData
    val failureResponse: MutableLiveData<String> get() = _failureResponse

    var validationError = MutableLiveData<String>()
    var isUserValid : Boolean = false

    fun registerUser(user: UserRegister){
        validateUser(user)
        if (isUserValid) fiwareRepository.register(user)
    }

    private fun validateUser (user: UserRegister) {
        when {
            !user.isFullNameLengthValid() -> validationError.value = "El nombre completo debe ser mayor a 5 y menor o igual a 15 caracteres."
            !user.isFullNameAlphabetic() -> validationError.value = "El nombre completo debe contener solamente letras."
            !user.isEmailLengthValid() -> validationError.value = "El email debe ser mayor o igual a 10 caracteres."
            !user.isEmailValid() -> validationError.value = "Debe ingresar un formato de email valido."
            !user.isPhoneNumberValid() -> validationError.value = "El teléfono debe ser mayor o igual a 8."
            !user.isOrganizationValid() -> validationError.value = "El nombre de la organización debe ser mayor o igual a 2 caracteres."
            !user.isPasswordValid() -> validationError.value = "La contraseña debe ser mayor o igual a 8 caracteres."
            !user.arePasswordsEquals() -> validationError.value = "Las contraseñas no coinciden."
            else -> {
                isUserValid = true
            }
        }
    }
}