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

    var validationError = MutableLiveData<String>()
    var isBranchOfficeValid : Boolean = false

    fun addBranchOffice(token: String, branchOffice: BranchOffice) {
        validateBranchOffice(branchOffice)
        if (isBranchOfficeValid) branchOfficesRepository.addBranchOffice(token, branchOffice)
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