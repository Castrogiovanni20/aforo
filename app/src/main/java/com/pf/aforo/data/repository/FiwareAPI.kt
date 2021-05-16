package com.pf.aforo.data.repository

import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.data.response.FiwareResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.pf.aforo.data.model.UserRegister

interface FiwareAPI {

    @POST("authentication")
    fun login(
        @Body user: UserLogin
    ) : Call<FiwareResponse>

    @POST("users")
    fun register(
        @Body user: UserRegister
    ) : Call<FiwareResponse>


    companion object {
        private val BASE_URL: String = "http://192.168.0.16:3000/api/v1/"

        operator fun invoke(): FiwareAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FiwareAPI::class.java)
        }
    }
}

object user {

}

