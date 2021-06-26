package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.repository.BranchOfficesRepository
import com.pf.aforo.data.repository.UsersRepository

class EditUserViewModel : ViewModel() {
    private var branchOfficesRepository = BranchOfficesRepository()
    private var usersRepository = UsersRepository()

    private var _deleteUserSuccessResponse = usersRepository.deleteUserSuccessResponseLiveData
    val deleteUserSuccessResponse: MutableLiveData<String> get() = _deleteUserSuccessResponse

    private var _deleteUserFailureResponse = usersRepository.deleteUserFailureResponseLiveData
    val deleteUserFailureResponse: MutableLiveData<String> get() = _deleteUserFailureResponse

    private var _updateUserRoleSuccessResponse = usersRepository.updateUserRoleSuccessResponseLiveData
    val updateUserRoleSuccessResponse: MutableLiveData<String> get() = _updateUserRoleSuccessResponse

    private var _updateUserRoleFailureResponse = usersRepository.updateUserRoleFailureResponseLiveData
    val updateUserRoleFailureResponse: MutableLiveData<String> get() = _updateUserRoleFailureResponse

    private var _removeCivilServantSuccessResponse = branchOfficesRepository.assignCivilServantSuccessResponseLiveData
    val removeCivilServantSuccessResponse: MutableLiveData<BranchOffice> get() = _removeCivilServantSuccessResponse

    private var _removeCivilServantFailureResponse = branchOfficesRepository.removeCivilServantFailureResponseLiveData
    val removeCivilServantFailureResponse: MutableLiveData<String> get() = _removeCivilServantFailureResponse

    fun deleteUser(token: String, id: String) {
        usersRepository.deleteUser(token, id)
    }

    fun updateUserRole(token: String, id: String, role: String) {
        usersRepository.updateUserRole(token, id, role)
    }

    fun removeCivilServant(token: String, entityId: String, refUser: String?) {
        branchOfficesRepository.removeCivilServant(token, entityId, refUser)
    }
}