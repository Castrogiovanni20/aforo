package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.repository.FiwareRepository

class HomeViewModel : ViewModel() {
    private var fiwareRepository = FiwareRepository()

    private var _getUsersResponse = fiwareRepository.getUsersSuccessResponseLiveData
    val getUsersResponse: MutableLiveData<Array<DataUser>> get() = _getUsersResponse

    private var _getUsersFailureResponse = fiwareRepository.getUsersFailureResponseLiveData
    val getUsersFailureResponse: MutableLiveData<String> get() = _getUsersFailureResponse

    fun getUsers(token: String) {
        fiwareRepository.getUsers(token)
    }

}