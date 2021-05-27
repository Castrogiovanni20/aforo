package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.repository.BranchOfficesRepository
import com.pf.aforo.data.repository.UsersRepository

class AddSucursalViewModel : ViewModel() {
    private val branchOfficesRepository = BranchOfficesRepository()
    private val usersRepository = UsersRepository()

    private var _addBranchOfficeSuccessResponse = branchOfficesRepository.addBranchOfficeSuccessResponseLiveData
    val addBranchOfficeSuccessResponse: MutableLiveData<BranchOffice> get() = _addBranchOfficeSuccessResponse

    private var _addBranchOfficeFailureResponse = branchOfficesRepository.addBranchOfficeFailureResponseLiveData
    val addBranchOfficeFailureResponse: MutableLiveData<String> get() = _addBranchOfficeFailureResponse

    fun addBranchOffice(token: String, branchOffice: BranchOffice) {
        branchOfficesRepository.addBranchOffice(token, branchOffice)
    }

    // To Do
    fun assignCivilServant() {

    }
}