package com.pf.aforo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.repository.FiwareRepository

//Estas son las variables observables + validaciones ac+a para sacar mayor cantidad de código del activitie:
class LoginViewModel : ViewModel() {
    private var fiwareRepository = FiwareRepository() //ver repository, para obtener acceso a login, registro

    private var _successResponse = fiwareRepository.loginSuccessResponseLiveData
    val successResponse: MutableLiveData<String> get() = _successResponse

    private var _failureResponse = fiwareRepository.loginFailureResponseLiveData
    val failureResponse: MutableLiveData<String> get() = _failureResponse

    fun loginUser (userName: String, password: String)  {
        if (userName != "" && password != "") {
            fiwareRepository.userLogin(userName, password)
        } else {
            failureResponse.postValue("400")
        }
    }

    //Continuar validaciones acá:
}