package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.Data
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.repository.UsersRepository

class EditProfileViewModel : ViewModel() {
    private var usersRepository = UsersRepository()

    private var _updateUserSuccessResponse = usersRepository.updateUserSuccessResponseLiveData
    val updateUserSuccessResponse: MutableLiveData<String> get() = _updateUserSuccessResponse

    private var _updateUserFailureResponse = usersRepository.updateUserFailureResponseLiveData
    val updateUserFailureResponse: MutableLiveData<String> get() = _updateUserFailureResponse

    private var _getUserSuccessResponse = usersRepository.getUserResponseLiveData
    val getUserSuccessResponse: MutableLiveData<DataUser> get() = _getUserSuccessResponse

    var validationError = MutableLiveData<String>()
    var isProfileValid : Boolean = false

    fun updateUser(token: String, userFuncionario: UserFuncionario) {
        validateProfile(userFuncionario)
        if (isProfileValid) usersRepository.updateUser(token, userFuncionario.id, userFuncionario)
    }

    private fun validateProfile(userFuncionario: UserFuncionario) {
        when {
            userFuncionario.lastName.isNullOrEmpty() -> validationError.value = "Debe ingresar su apellido."
            !userFuncionario.isLastNameLengthValid() -> validationError.value = "El apellido debe ser mayor a 2 y menor o igual a 60 caracteres."
            userFuncionario.firstName.isNullOrEmpty() -> validationError.value = "Debe ingresar su nombre."
            !userFuncionario.isFirstNameLengthValid() -> validationError.value = "El nombre debe ser mayor a 2 y menor o igual a 60 caracteres."
            userFuncionario.phoneNumber.isNullOrEmpty() -> validationError.value = "Debe ingresar su número de teléfono."
            !userFuncionario.isPhoneNumberValid() -> validationError.value = "Debe ingresar un número de teléfono válido."
            else -> {
                isProfileValid = true
            }
        }
    }

    fun getUser(token: String, userId: String) {
        usersRepository.getUser(token, userId)
    }
}