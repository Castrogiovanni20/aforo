package com.pf.aforo.data.repository

import com.pf.aforo.data.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FiwareAPI {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body user: User): Call<User>
}

class RetrofitClient {
    companion object {
        private var instance : FiwareAPI? = null

        fun getInstance(): FiwareAPI {
            if (instance == null)
                instance = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://yourbaseurl.com")
                    .build()
                    .create(FiwareAPI::class.java)
            return instance as FiwareAPI
        }
    }
}

