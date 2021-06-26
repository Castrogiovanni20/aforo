package com.pf.aforo.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.repository.UsersRepository
import java.util.*

class SplashViewModel : ViewModel(){
    private var usersRepository = UsersRepository()
    val flag = MutableLiveData<Boolean>()

    private var _userResponseLiveData = usersRepository.getUserResponseLiveData
    val userResponseLiveData: MutableLiveData<DataUser> get() = _userResponseLiveData

    private var _getUserFailureResponse = usersRepository.getUserFailureResponseLiveData
    val getUserFailureResponse: MutableLiveData<String> get() = _getUserFailureResponse

    fun startTimer () {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                flag.postValue(true);
            }
        }, 2000)
    }

    fun getUser(token: String, userId: String) {
        usersRepository.getUser(token, userId)
    }
}