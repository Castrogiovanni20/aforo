package com.pf.aforo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FiwareRepository()
{
    fun userLogin(email: String, password: String) : MutableLiveData<String> {
        val loginResponse = MutableLiveData<String>()

        FiwareAPI().userLogin(email, password)
            .enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    if (t != null) {
                        loginResponse.value = t.message
                    }
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>? ){
                    if (response != null) {
                        if (response.isSuccessful) {
                            loginResponse.value = response.message()
                        } else {
                            loginResponse.value = response.errorBody().string()
                        }
                    }
                }

            })

        return loginResponse
    }

}