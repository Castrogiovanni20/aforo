package com.pf.aforo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.Data
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.data.repository.AuthenticationRepository
import com.pf.aforo.data.repository.UsersRepository

//Estas son las variables observables + validaciones ac+a para sacar mayor cantidad de código del activitie:
class LoginViewModel : ViewModel() {
    private var authenticationRepository = AuthenticationRepository() //ver repository, para obtener acceso a login, registro
    private var usersRepository = UsersRepository()

    private var _loginDataResponseLiveData = authenticationRepository.loginDataResponseLiveData
    val loginDataResponseLiveData: MutableLiveData<Data> get() = _loginDataResponseLiveData

    private var _failureResponse = authenticationRepository.loginFailureResponseLiveData
    val failureResponse: MutableLiveData<String> get() = _failureResponse

    private var _userResponseLiveData = usersRepository.getUserResponseLiveData
    val userResponseLiveData: MutableLiveData<DataUser> get() = _userResponseLiveData

    private var _getUserFailureResponse = usersRepository.getUserFailureResponseLiveData
    val getUserFailureResponse: MutableLiveData<String> get() = _getUserFailureResponse

    val isLoading = MutableLiveData<Boolean>()

    val startProgressBar = MutableLiveData<Boolean>()
    val stopProgressBar = MutableLiveData<Boolean>()

    var validationError = MutableLiveData<String>()
    var isUserValid : Boolean = false

    private var _updateUserSuccessResponseLiveData = usersRepository.updateUserSuccessResponseLiveData
    val  updateUserSuccessResponseLiveData: MutableLiveData<String> get() = _updateUserSuccessResponseLiveData

    private var _updateUserFailureResponseLiveData = usersRepository.updateUserFailureResponseLiveData
    val updateUserFailureResponseLiveData: MutableLiveData<String> get() = _updateUserFailureResponseLiveData


    init {
        isLoading.value = false
    }

    fun loginUser (user: UserLogin)  {
        isLoading.value = true
        validateUser(user)
        if (isUserValid) {
            authenticationRepository.login(user)
        }
    }

    fun getUser(token: String, userId: String) {
        usersRepository.getUser(token, userId)
    }

    private fun validateUser(user: UserLogin) {
        when {
            !user.isUserNameValid() -> validationError.value = "Debe ingresar su email."
            !user.isEmailValid() -> validationError.value = "Debe ingresar un formato de email valido."
            !user.isPasswordValid() -> validationError.value = "Debe ingresar su contraseña."
            else -> {
                isUserValid = true
            }
        }
    }

    fun startProgressBar() {
        startProgressBar.value = true
    }

    fun stopProgressBar() {
        stopProgressBar.value = true
    }

    fun updateUser(token: String, id_user: String, user_funcionario: UserFuncionario) {
        usersRepository.updateUser(token, id_user, user_funcionario)
    }

}