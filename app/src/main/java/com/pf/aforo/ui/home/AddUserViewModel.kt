package com.pf.aforo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.repository.FiwareRepository

class AddUserViewModel : ViewModel() {
    private var fiwareRepository = FiwareRepository()

    private var _successResponse = fiwareRepository.addUserSuccessResponseLiveData
    val successResponse: MutableLiveData<String> get() = _successResponse

    private var _failureResponse = fiwareRepository.addUserFailureResponseLiveData
    val failureResponse: MutableLiveData<String> get() = _failureResponse

    var validationError = MutableLiveData<String>()
    var isUserValid : Boolean = false

    fun addUser(userFuncionario: UserFuncionario) {
        validateUser(userFuncionario)
        fiwareRepository.addUser(userFuncionario)
    }

    private fun validateUser(user: UserFuncionario) {
        when {
            !user.isFirstNameLengthValid() -> validationError.value = "El nombre debe ser mayor a 2 y menor o igual a 60 caracteres."
            !user.isFirstNameAlphabetic() -> validationError.value = "El nombre debe contener solamente letras."
            !user.isLastNameLengthValid() -> validationError.value = "El apellido debe ser mayor a 2 y menor o igual a 60 caracteres."
            !user.isLastNameAlphabetic() -> validationError.value = "El apellido debe contener solamente letras."
            !user.isPhoneNumberValid() -> validationError.value = "El teléfono celular debe ser menor o igual a 15 caracteres."
            !user.isEmailValid() -> validationError.value = "Debe ingresar un formato de email valido."
            !user.isPasswordValid() -> validationError.value = "La contraseña debe ser mayor o igual a 8 caracteres."
            else -> {
                isUserValid = true
            }
        }
    }
}