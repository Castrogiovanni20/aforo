package com.pf.aforo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.repository.FiwareAPI
import com.pf.aforo.data.repository.FiwareRepository

class HomeViewModel : ViewModel() {
    private var fiwareRepository = FiwareRepository()

    private var _usersResponse = fiwareRepository.usersResponseLiveData
    val usersResponse: MutableLiveData<Array<DataUser>> get() = _usersResponse

    private var _failureResponse = fiwareRepository.getUserFailureResponseLiveData
    val failureResponse: MutableLiveData<String> get() = _failureResponse

    fun getUsers(token: String) {
        fiwareRepository.getUsers(token)
    }

}