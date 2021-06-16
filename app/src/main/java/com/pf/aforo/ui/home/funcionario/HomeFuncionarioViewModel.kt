package com.pf.aforo.ui.home.funcionario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.repository.BranchOfficesRepository

class HomeFuncionarioViewModel : ViewModel(){
    private var branchOfficesRepository = BranchOfficesRepository()

    private var _getBranchOfficeByIdResponse = branchOfficesRepository.getBranchOfficeByIdSuccessResponseLiveData
    val getBranchOfficeByIdResponse: MutableLiveData<BranchOffice> get() = _getBranchOfficeByIdResponse

    private var _getBranchOfficeByIdFailureResponse = branchOfficesRepository.getBranchOfficeByIdFailureResponseLiveData
    val getBranchOfficeByIdFailureResponse: MutableLiveData<String> get() = _getBranchOfficeByIdFailureResponse

    fun getBranchOfficeById(token: String, id: String) {
        branchOfficesRepository.getBranchOfficeById(token, id)
    }
}