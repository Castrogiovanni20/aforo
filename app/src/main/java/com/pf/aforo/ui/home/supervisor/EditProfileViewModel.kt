package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.repository.UsersRepository

class EditProfileViewModel : ViewModel() {
    private var usersRepository = UsersRepository()

    private var _updateUserSuccessResponse = usersRepository.updateUserSuccessResponseLiveData
    val updateUserSuccessResponse: MutableLiveData<String> get() = _updateUserSuccessResponse

    private var _updateUserFailureResponse = usersRepository.updateUserFailureResponseLiveData
    val updateUserFailureResponse: MutableLiveData<String> get() = _updateUserFailureResponse

    fun updateUser(token: String, userFuncionario: UserFuncionario) {
        usersRepository.updateUser(token, userFuncionario.id, userFuncionario)
    }
}