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

    fun addUser(userFuncionario: UserFuncionario) {
        validateUser(userFuncionario)
        fiwareRepository.addUser(userFuncionario)
    }

    private fun validateUser(user: UserFuncionario) {
        /* Validaciones
         validationError.value = "<Mensaje de error>" */
    }
}