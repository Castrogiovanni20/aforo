package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.repository.BranchOfficesRepository
import com.pf.aforo.data.repository.UsersRepository

class AddSucursalViewModel : ViewModel() {
    private val branchOfficesRepository = BranchOfficesRepository()
    private val usersRepository = UsersRepository()

    private var _addBranchOfficeSuccessResponse = branchOfficesRepository.addBranchOfficeSuccessResponseLiveData
    val addBranchOfficeSuccessResponse: MutableLiveData<BranchOffice> get() = _addBranchOfficeSuccessResponse

    private var _addBranchOfficeFailureResponse = branchOfficesRepository.addBranchOfficeFailureResponseLiveData
    val addBranchOfficeFailureResponse: MutableLiveData<String> get() = _addBranchOfficeFailureResponse

    private var _getUsersSuccessResponse = usersRepository.getUsersSuccessResponseLiveData
    val getUsersSuccessResponse: MutableLiveData<Array<DataUser>> get() = _getUsersSuccessResponse

    private var _getUsersFailureResponse = usersRepository.getUserFailureResponseLiveData
    val getUsersFailureResponse: MutableLiveData<String> get() = _getUsersFailureResponse

    private var _assignCivilServantSuccessResponse = branchOfficesRepository.assignCivilServantSuccessResponseLiveData
    val assignCivilServantSuccessResponse: MutableLiveData<BranchOffice> get() = _assignCivilServantSuccessResponse

    private var _assignCivilServantFailureResponse = branchOfficesRepository.assignCivilServantFailureResponseLiveData
    val assignCivilServantFailureResponse: MutableLiveData<String> get() = _assignCivilServantFailureResponse

    fun addBranchOffice(token: String, branchOffice: BranchOffice) {
        branchOfficesRepository.addBranchOffice(token, branchOffice)
    }

    fun assignCivilServant(token: String, entityId: String, refUser: String) {
        branchOfficesRepository.assignCivilServant(token, entityId, refUser)
    }

    fun getUsers(token: String) {
        usersRepository.getUsers(token)
    }
}