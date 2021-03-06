package com.pf.aforo.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.UserSupervisor
import com.pf.aforo.data.repository.AuthenticationRepository

class RegisterViewModel : ViewModel () {
    private var authenticationRepository = AuthenticationRepository()

    private var _successResponse = authenticationRepository.registerSuccessResponseLiveData
    val successResponse: MutableLiveData<String> get() = _successResponse

    private var _failureResponse = authenticationRepository.registerFailureResponseLiveData
    val failureResponse: MutableLiveData<String> get() = _failureResponse

    val startProgressBar = MutableLiveData<Boolean>()
    val stopProgressBar = MutableLiveData<Boolean>()

    var validationError = MutableLiveData<String>()
    var isUserValid : Boolean = false

    init {
        startProgressBar.value = false
        stopProgressBar.value = false
    }

    fun registerUser(user: UserSupervisor){
        validateUser(user)
        if (isUserValid) authenticationRepository.register(user)
    }

    private fun validateUser (user: UserSupervisor) {
        when {
            !user.isFirstNameLengthValid() -> validationError.value = "El nombre debe ser mayor a 2 y menor o igual a 60 caracteres."
            !user.isFirstNameAlphabetic() -> validationError.value = "El nombre debe contener solamente letras."
            !user.isLastNameLengthValid() -> validationError.value = "El apellido debe ser mayor a 2 y menor o igual a 60 caracteres."
            !user.isLastNameAlphabetic() -> validationError.value = "El apellido debe contener solamente letras."
            !user.isEmailValid() -> validationError.value = "Debe ingresar un formato de email valido."
            user.phoneNumber.isNullOrEmpty() -> validationError.value = "Debe ingresar un número de teléfono."
            !user.isPhoneNumberValid() -> validationError.value = "Debe ingresar un número de teléfono válido."
            !user.isrefOrganizationValid() -> validationError.value = "El nombre de la organización debe ser mayor a 3 y menor a 41 caracteres."
            !user.isPasswordValid() -> validationError.value = "La contraseña debe ser mayor o igual a 8 caracteres."
            !user.arePasswordsEquals() -> validationError.value = "Las contraseñas no coinciden."
            else -> {
                isUserValid = true
            }
        }
    }

    fun startProgressBar() {
        startProgressBar.value = true
    }

    fun stopProgressBar() {
        stopProgressBar.value = true
    }
}