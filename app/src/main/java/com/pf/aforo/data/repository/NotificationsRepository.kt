package com.pf.aforo.data.repository

import android.telephony.SubscriptionInfo
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pf.aforo.data.model.SocketId
import com.pf.aforo.data.model.SubscriptionId
import com.pf.aforo.data.response.FiwareResponse
import com.pf.aforo.data.response.FiwareResponseSubscribe
import com.pf.aforo.data.response.FiwareResponseUnsubscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsRepository {

    var subscriptionIdResponseLiveData = MutableLiveData<String>()

    fun subscribe(token: String, socketId: SocketId) {
        FiwareAPI().subscribe(token, socketId)
            .enqueue(object: Callback<FiwareResponseSubscribe> {
                override fun onFailure(call: Call<FiwareResponseSubscribe>?, t: Throwable?) {
                    if (t != null) {
                        Log.d("ApiSubscribe", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseSubscribe>?, fiwareResponse: Response<FiwareResponseSubscribe>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                subscriptionIdResponseLiveData.value = fiwareResponse.body().data.subscriptionId
                                Log.d("ApiSubscribe", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            Log.d("ApiSubscribe", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun unsubscribe(token: String, subscriptionId: SubscriptionId) {
        FiwareAPI().unsubscribe(token, subscriptionId)
            .enqueue(object: Callback<FiwareResponseUnsubscribe> {
                override fun onFailure(call: Call<FiwareResponseUnsubscribe>?, t: Throwable?) {
                    if (t != null) {
                        Log.d("ApiUnsubscribe", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseUnsubscribe>?, fiwareResponse: Response<FiwareResponseUnsubscribe>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                subscriptionIdResponseLiveData.value = fiwareResponse.body().data.subscriptionId
                                Log.d("ApiUnsubscribe", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            Log.d("ApiUnsubscribe", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }


}