package com.pf.aforo.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.data.model.UserRegister
import com.pf.aforo.data.response.FiwareResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FiwareRepository()
{
    var loginSuccessResponseLiveData = MutableLiveData<String>()
    var loginFailureResponseLiveData = MutableLiveData<String>()
    var registerSuccessResponseLiveData = MutableLiveData<String>()
    var registerFailureResponseLiveData = MutableLiveData<String>()

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

    fun register(user: UserRegister) {
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

}