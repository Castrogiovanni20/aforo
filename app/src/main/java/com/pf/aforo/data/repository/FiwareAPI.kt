package com.pf.aforo.data.repository

import com.pf.aforo.data.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface FiwareAPI {

    @FormUrlEncoded
    @POST("users")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<ResponseBody>

    companion object {
        private val BASE_URL: String = "https://608dea67fe2e9c00171e20f6.mockapi.io/api/v1/"

        operator fun invoke(): FiwareAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FiwareAPI::class.java)
        }
    }
}

