package com.pf.aforo.ui.home.supervisor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.repository.BranchOfficesRepository
import com.pf.aforo.data.repository.UsersRepository

class EditSucursalViewModel : ViewModel() {
    private var branchOfficesRepository = BranchOfficesRepository()
    private var usersRepository = UsersRepository()

    private var _updateBranchOfficeSuccessResponse = branchOfficesRepository.updateBranchOfficeSuccessResponseLiveData
    val updateBranchOfficeSuccessResponse: MutableLiveData<String> get() = _updateBranchOfficeSuccessResponse

    private var _updateBranchOfficeFailureResponse = branchOfficesRepository.updateBranchOfficeFailureResponseLiveData
    val updateBranchOfficeFailureResponse: MutableLiveData<String> get() = _updateBranchOfficeFailureResponse

    private var _deleteBranchOfficeSuccessResponse = branchOfficesRepository.deleteBranchOfficeSuccessResponseLiveData
    val deleteBranchOfficeSuccessResponse: MutableLiveData<String> get() = _deleteBranchOfficeSuccessResponse

    private var _deleteBranchOfficeFailureResponse = branchOfficesRepository.deleteBranchOfficeFailureResponseLiveData
    val deleteBranchOfficeFailureResponse: MutableLiveData<String> get() = _deleteBranchOfficeFailureResponse

    private var _getUsersSuccessResponse = usersRepository.getUsersSuccessResponseLiveData
    val getUsersSuccessResponse: MutableLiveData<Array<DataUser>> get() = _getUsersSuccessResponse

    private var _getUsersFailureResponse = usersRepository.getUserFailureResponseLiveData
    val getUsersFailureResponse: MutableLiveData<String> get() = _getUsersFailureResponse

    private var _assignCivilServantSuccessResponse = branchOfficesRepository.assignCivilServantSuccessResponseLiveData
    val assignCivilServantSuccessResponse: MutableLiveData<BranchOffice> get() = _assignCivilServantSuccessResponse

    private var _assignCivilServantFailureResponse = branchOfficesRepository.assignCivilServantFailureResponseLiveData
    val assignCivilServantFailureResponse: MutableLiveData<String> get() = _assignCivilServantFailureResponse

    var validationError = MutableLiveData<String>()
    var isBranchOfficeValid : Boolean = false

    fun updateBranchOffice(token: String, id: String, branchOffice: BranchOffice) {
        validateBranchOffice(branchOffice)
        if (isBranchOfficeValid) branchOfficesRepository.updateBranchOffice(token, id, branchOffice)
    }

    fun deleteBranchOffice(token: String, id: String) {
        branchOfficesRepository.deleteBranchOffice(token, id)
    }

    fun assignCivilServant(token: String, entityId: String, refUser: String) {
        branchOfficesRepository.assignCivilServant(token, entityId, refUser)
    }

    fun getUsers(token: String) {
        usersRepository.getUsers(token)
    }

    private fun validateBranchOffice(branchOffice: BranchOffice) {
        when {
            branchOffice.name.isNullOrEmpty() -> validationError.value = "Debe completar el nombre."
            !branchOffice.isNameLengthValid() -> validationError.value = "El nombre debe ser menor o igual a 120 caracteres."
            branchOffice.description.isNullOrEmpty() -> validationError.value = "Debe completar la dirección."
            branchOffice.width == null -> validationError.value = "Debe completar el ancho."
            branchOffice.length == null -> validationError.value = "Debe completar el largo."
            !branchOffice.isWidthValid() -> validationError.value = "El ancho debe ser mayor o igual a 1."
            !branchOffice.isLengthValid() -> validationError.value = "El largo debe ser mayor o igual a 1."
            else -> {
                isBranchOfficeValid = true
            }
        }
    }

}