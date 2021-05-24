package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.repository.FiwareRepository

class EditUserViewModel : ViewModel() {
    private var fiwareRepository = FiwareRepository()

    private var _deleteUserSuccessResponse = fiwareRepository.deleteUserSuccessResponseLiveData
    val deleteUserSuccessResponse: MutableLiveData<String> get() = _deleteUserSuccessResponse

    private var _deleteUserFailureResponse = fiwareRepository.deleteUserFailureResponseLiveData
    val deleteUserFailureResponse: MutableLiveData<String> get() = _deleteUserFailureResponse

    private var _updateUserRoleSuccessResponse = fiwareRepository.updateUserRoleSuccessResponseLiveData
    val updateUserRoleSuccessResponse: MutableLiveData<String> get() = _updateUserRoleSuccessResponse

    private var _updateUserRoleFailureResponse = fiwareRepository.updateUserRoleFailureResponseLiveData
    val updateUserRoleFailureResponse: MutableLiveData<String> get() = _updateUserRoleFailureResponse

    fun deleteUser(token: String, id: String) {
        fiwareRepository.deleteUser(token, id)
    }

    fun updateUserRole(token: String, id: String, role: String) {
        fiwareRepository.updateUserRole(token, id, role)
    }
}