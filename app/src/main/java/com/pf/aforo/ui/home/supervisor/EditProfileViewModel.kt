package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.repository.FiwareRepository

class EditProfileViewModel : ViewModel() {
    private var fiwareRepository = FiwareRepository()

    private var _updateUserSuccessResponse = fiwareRepository.updateUserSuccessResponseLiveData
    val updateUserSuccessResponse: MutableLiveData<String> get() = _updateUserSuccessResponse

    private var _updateUserFailureResponse = fiwareRepository.updateUserFailureResponseLiveData
    val updateUserFailureResponse: MutableLiveData<String> get() = _updateUserFailureResponse

    fun updateUser(token: String, userFuncionario: UserFuncionario) {
        fiwareRepository.updateUser(token, userFuncionario.id, userFuncionario)
    }
}