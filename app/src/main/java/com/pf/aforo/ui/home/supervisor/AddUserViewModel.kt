package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.repository.UsersRepository

class AddUserViewModel : ViewModel() {
    private var usersRepository = UsersRepository()

    private var _successResponse = usersRepository.addUserSuccessResponseLiveData
    val successResponse: MutableLiveData<String> get() = _successResponse

    private var _failureResponse = usersRepository.addUserFailureResponseLiveData
    val failureResponse: MutableLiveData<String> get() = _failureResponse

    var validationError = MutableLiveData<String>()
    var isUserValid : Boolean = false

    fun addUser(token: String, userFuncionario: UserFuncionario) {
        validateUser(userFuncionario)
        if (isUserValid) usersRepository.addUser(token, userFuncionario)
    }

    private fun validateUser(user: UserFuncionario) {
        when {
            !user.isFirstNameLengthValid() -> validationError.value = "El nombre debe ser mayor a 2 y menor o igual a 60 caracteres."
            !user.isFirstNameAlphabetic() -> validationError.value = "El nombre debe contener solamente letras."
            !user.isLastNameLengthValid() -> validationError.value = "El apellido debe ser mayor a 2 y menor o igual a 60 caracteres."
            !user.isLastNameAlphabetic() -> validationError.value = "El apellido debe contener solamente letras."
            user.identificationNumber.isNullOrEmpty() -> validationError.value = "Debe ingresar un número DNI."
            !user.isIdentificationNumberValid() -> validationError.value = "Debe ingresar un número DNI válido."
            user.phoneNumber.isNullOrEmpty() -> validationError.value = "Debe ingresar un número de teléfono."
            !user.isPhoneNumberValid() -> validationError.value = "Debe ingresar un número de teléfono válido."
            !user.isEmailValid() -> validationError.value = "Debe ingresar un formato de email valido."
            !user.isPasswordValid() -> validationError.value = "La contraseña debe ser mayor o igual a 8 caracteres."
            !user.arePasswordsEquals() -> validationError.value = "Las contraseñas no coinciden."
            else -> {
                isUserValid = true
            }
        }
    }
}