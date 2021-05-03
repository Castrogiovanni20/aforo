package com.pf.aforo.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FiwareRepository()
{
    var loginResponseLiveData = MutableLiveData<String>()

    fun userLogin(email: String, password: String) {
        FiwareAPI().userLogin(email, password)
            .enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    if (t != null) {
                        loginResponseLiveData.value = t.message
                        Log.d("ApiResponse", "Fallo la API" + t.message);
                    }
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>? ){
                    if (response != null) {
                        if (response.isSuccessful) {
                            loginResponseLiveData.value = response.message()
                            Log.d("ApiResponse", "Respondio la API " + response.code());
                        } else {
                            loginResponseLiveData.value = response.errorBody().string()
                        }
                    }
                }

            })
    }

}