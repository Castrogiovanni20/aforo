package com.pf.aforo.ui.home.supervisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.Settings
import com.pf.aforo.data.model.UserSettings
import com.pf.aforo.data.repository.UsersRepository

class NotificacionesSupervisorViewModel : ViewModel() {
    private var usersRepository = UsersRepository()

    private var _getUserSuccessResponse = usersRepository.getUserResponseLiveData
    val getUserSuccessResponse: MutableLiveData<DataUser> get() = _getUserSuccessResponse

    private var _getUserFailureResponse = usersRepository.getUserFailureResponseLiveData
    val getUserFailureResponse: MutableLiveData<String> get() = _getUserFailureResponse

    private var _updateSettingsSuccessResponse = usersRepository.updateSettingsSuccessResponseLiveData
    val updateSettingsSuccessResponse: MutableLiveData<String> get() = _updateSettingsSuccessResponse

    private var _updateSettingsFailureResponse = usersRepository.updateSettingsFailureResponseLiveData
    val updateSettingsFailureResponse: MutableLiveData<String> get() = _updateSettingsFailureResponse

    fun getUser(token: String, userId: String) {
        usersRepository.getUser(token, userId)
    }

    fun updateSettings(token: String, userId: String, settings: UserSettings) {
        usersRepository.updateSettings(token, userId, settings)
    }
}