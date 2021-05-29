package com.pf.aforo.ui.home.supervisor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.repository.BranchOfficesRepository
import com.pf.aforo.data.repository.UsersRepository
import com.pf.aforo.data.repository.user

class EditSucursalViewModel : ViewModel() {
    private var branchOfficesRepository = BranchOfficesRepository()
    private var usersRepository = UsersRepository()

    private var _updateBranchOfficeSuccessResponse = branchOfficesRepository.updateBranchOfficeSuccessResponseLiveData
    val updateBranchOfficeSuccessResponse: MutableLiveData<String> get() = _updateBranchOfficeSuccessResponse

    private var _updateBranchOfficeFailureResponse = branchOfficesRepository.updateBranchOfficeFailureResponseLiveData
    val updateBranchOfficeFailureResponse: MutableLiveData<String> get() = _updateBranchOfficeFailureResponse

    private var _getUsersSuccessResponse = usersRepository.getUsersSuccessResponseLiveData
    val getUsersSuccessResponse: MutableLiveData<Array<DataUser>> get() = _getUsersSuccessResponse

    private var _getUsersFailureResponse = usersRepository.getUserFailureResponseLiveData
    val getUsersFailureResponse: MutableLiveData<String> get() = _getUsersFailureResponse

    fun updateBranchOffice(token: String, id: String, branchOffice: BranchOffice) {
        branchOfficesRepository.updateBranchOffice(token, id, branchOffice)
    }

    fun getUsers(token: String) {
        usersRepository.getUsers(token)
    }

}