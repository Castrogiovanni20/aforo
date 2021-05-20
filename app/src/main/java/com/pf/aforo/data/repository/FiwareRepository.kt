package com.pf.aforo.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.data.model.UserSupervisor
import com.pf.aforo.data.response.FiwareResponse
import com.pf.aforo.data.response.FiwareResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FiwareRepository()
{
    var loginSuccessResponseLiveData = MutableLiveData<String>()
    var loginFailureResponseLiveData = MutableLiveData<String>()
    var registerSuccessResponseLiveData = MutableLiveData<String>()
    var registerFailureResponseLiveData = MutableLiveData<String>()
    var addUserSuccessResponseLiveData = MutableLiveData<String>()
    var addUserFailureResponseLiveData = MutableLiveData<String>()
    var usersResponseLiveData = MutableLiveData<Array<DataUser>>()
    var getUserFailureResponseLiveData = MutableLiveData<String>()

    fun login(userLogin: UserLogin) {
        FiwareAPI().login(userLogin)
            .enqueue(object: Callback<FiwareResponse>{
                override fun onFailure(call: Call<FiwareResponse>?, t: Throwable?) {
                    if (t != null) {
                        loginFailureResponseLiveData.value = "404"
                        Log.d("ApiLoginResponse", "Fallo el request" + t.message);
                    }
                }

                override fun onResponse(call: Call<FiwareResponse>?, fiwareResponse: Response<FiwareResponse>? ){
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().getData().getToken() != null) {
                                loginSuccessResponseLiveData.value = fiwareResponse.body().getData().getToken()
                                Log.d("ApiLoginResponse", "Respondio la API " + fiwareResponse.body().getData().getToken());
                            }
                        } else {
                            loginFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiLoginResponse", "Respondio la API " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun register(user: UserSupervisor) {
        FiwareAPI().register(user)
            .enqueue(object: Callback<FiwareResponse>{
                override fun onFailure(call: Call<FiwareResponse>?, t: Throwable?) {
                    if (t != null) {
                        registerFailureResponseLiveData.value = "404"
                        Log.d("ApiRegisterResponse", "Fallo el request" + t.message)
                    }
                }

                override fun onResponse( call: Call<FiwareResponse>?, fiwareResponse: Response<FiwareResponse>? ) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().getCode() == "SUCCESS") {
                                registerSuccessResponseLiveData.value = fiwareResponse.code().toString()
                                Log.d("ApiRegisterResponse", "Respondio la API " + fiwareResponse.code().toString())
                            }
                        } else {
                            registerFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiRegisterResponse", "Respondio la API " + fiwareResponse.code().toString())
                        }
                    }
                }
            })
    }

    fun addUser(user: UserFuncionario){
        FiwareAPI().addUser(user.getToken(), user)
            .enqueue(object: Callback<FiwareResponse>{
                override fun onFailure(call: Call<FiwareResponse>?, t: Throwable?) {
                    if (t != null) {
                        addUserFailureResponseLiveData.value = "404"
                        Log.d("ApiNewUser", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponse>?, fiwareResponse: Response<FiwareResponse>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().getCode() == "SUCCESS") {
                                addUserSuccessResponseLiveData.value = fiwareResponse.code().toString()
                                Log.d("ApiNewUser", "Respondio la API " + fiwareResponse.code().toString())
                            }
                        } else {
                            addUserFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiNewUser", "Respondio la API " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun getUsers(token: String){
        FiwareAPI().getUsers(token)
            .enqueue(object: Callback<FiwareResponseUser>{
                override fun onFailure(call: Call<FiwareResponseUser>?, t: Throwable?) {
                    if (t != null) {
                        getUserFailureResponseLiveData.value = "404"
                        Log.d("ApiGetUser", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseUser>?, fiwareResponse: Response<FiwareResponseUser>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().getCode() == "SUCCESS") {
                                var response = fiwareResponse.body().getData()
                                usersResponseLiveData.value = response
                                Log.d("ApiGetUser", "Respondio la API " + fiwareResponse.code().toString())
                            }
                        } else {
                            getUserFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiGetUser", "Respondio la API " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

}