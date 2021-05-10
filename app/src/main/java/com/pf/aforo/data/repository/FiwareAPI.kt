package com.pf.aforo.data.repository

import com.pf.aforo.data.response.FiwareResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.pf.aforo.data.model.User

interface FiwareAPI {

    @FormUrlEncoded
    @POST("authentication")
    fun userLogin(
        @Field("userName") email: String,
        @Field("password") password: String
    ) : Call<FiwareResponse>

    @POST("users")
    fun userRegister(
        @Body user: User
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

