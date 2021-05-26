package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.repository.UsersRepository

class HomeViewModel : ViewModel() {
    private var usersRepository = UsersRepository()

    private var _getUsersResponse = usersRepository.getUsersSuccessResponseLiveData
    val getUsersResponse: MutableLiveData<Array<DataUser>> get() = _getUsersResponse

    private var _getUsersFailureResponse = usersRepository.getUsersFailureResponseLiveData
    val getUsersFailureResponse: MutableLiveData<String> get() = _getUsersFailureResponse

    fun getUsers(token: String) {
        usersRepository.getUsers(token)
    }

}