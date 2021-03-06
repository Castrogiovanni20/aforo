package com.pf.aforo.ui.home.funcionario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataBranchOfficeHistory
import com.pf.aforo.data.model.SocketId
import com.pf.aforo.data.repository.BranchOfficesRepository
import com.pf.aforo.data.repository.NotificationsRepository

class HomeFuncionarioViewModel : ViewModel(){
    private var branchOfficesRepository = BranchOfficesRepository()
    private var notificationsRepository = NotificationsRepository()

    private var _getBranchOfficeByIdResponse = branchOfficesRepository.getBranchOfficeByIdSuccessResponseLiveData
    val getBranchOfficeByIdResponse: MutableLiveData<BranchOffice> get() = _getBranchOfficeByIdResponse

    private var _getBranchOfficeByIdFailureResponse = branchOfficesRepository.getBranchOfficeByIdFailureResponseLiveData
    val getBranchOfficeByIdFailureResponse: MutableLiveData<String> get() = _getBranchOfficeByIdFailureResponse

    private var _getBranchOfficeHistoryResponse = branchOfficesRepository.getBranchOfficeHistorySuccessResponseLiveData
    val getBranchOfficeHistoryResponse: MutableLiveData<Array<DataBranchOfficeHistory>> get() = _getBranchOfficeHistoryResponse

    private var _getBranchOfficeHistoryFailureResponse = branchOfficesRepository.getBranchOfficeHistoryFailureResponseLiveData
    val getBranchOfficeHistoryFailureResponse: MutableLiveData<String> get() = _getBranchOfficeHistoryFailureResponse

    private var _subscriptionId = notificationsRepository.subscriptionIdResponseLiveData
    val subscriptionIdResponse: MutableLiveData<String> get() = _subscriptionId

    fun getBranchOfficeById(token: String, id: String) {
        branchOfficesRepository.getBranchOfficeById(token, id)
    }

    fun getBranchOfficeHistory(token: String, id: String) {
        branchOfficesRepository.getBranchOfficeHistory(token, id)
    }

    fun getSubscriptionId(token: String, socketId: SocketId) {
        notificationsRepository.subscribe(token, socketId)
    }
}