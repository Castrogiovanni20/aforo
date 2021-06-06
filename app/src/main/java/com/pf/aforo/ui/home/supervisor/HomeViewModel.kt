package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.repository.BranchOfficesRepository
import com.pf.aforo.data.repository.UsersRepository

class HomeViewModel : ViewModel() {
    private var usersRepository = UsersRepository()
    private var branchOfficesRepository = BranchOfficesRepository()

    private var _getUsersResponse = usersRepository.getUsersSuccessResponseLiveData
    val getUsersResponse: MutableLiveData<Array<DataUser>> get() = _getUsersResponse

    private var _getUsersFailureResponse = usersRepository.getUsersFailureResponseLiveData
    val getUsersFailureResponse: MutableLiveData<String> get() = _getUsersFailureResponse

    private var _getBranchOfficesResponse = branchOfficesRepository.getBranchOfficeSuccessResponseLiveData
    val getBranchOfficesResponse: MutableLiveData<ArrayList<BranchOffice>> get() = _getBranchOfficesResponse

    private var _getBranchOfficesFailureResponse = branchOfficesRepository.getBranchOfficesFailureResponseLiveData
    val getBranchOfficesFailureResponse: MutableLiveData<String> get() = _getBranchOfficesFailureResponse

    fun getUsers(token: String) {
        usersRepository.getUsers(token)
    }

    fun getBranchOffices(token: String) {
        branchOfficesRepository.getBranchOffices(token)
    }

}