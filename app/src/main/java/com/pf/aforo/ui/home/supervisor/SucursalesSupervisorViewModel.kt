package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.repository.BranchOfficesRepository

class SucursalesSupervisorViewModel : ViewModel() {
    private  var branchOfficesRepository = BranchOfficesRepository()

    private var _getBranchOffices = branchOfficesRepository.getBranchOfficeSuccessResponseLiveData
    val getBranchOfficesResponse: MutableLiveData<ArrayList<BranchOffice>> get() = _getBranchOffices

    private var _getBranchOfficeFailureResponse = branchOfficesRepository.getBranchOfficesFailureResponseLiveData
    val getBranchOfficeFailureResponse: MutableLiveData<String> get() = _getBranchOfficeFailureResponse

    fun getBranchOffices(token: String) {
        branchOfficesRepository.getBranchOffices(token)
    }

}