package com.pf.aforo.data.repository

import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.data.model.UserSupervisor
import com.pf.aforo.data.response.FiwareResponse
import com.pf.aforo.data.response.FiwareResponseUser
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface FiwareAPI {

    @POST("authentication/signin")
    fun login(
        @Body user: UserLogin
    ) : Call<FiwareResponse>

    @POST("authentication/signup")
    fun register(
        @Body user: UserSupervisor
    ) : Call<FiwareResponse>

    @POST("users")
    fun addUser(
        @Header("Authorization") token: String,
        @Body user: UserFuncionario
    ) : Call<FiwareResponse>

    @GET("users/organization")
    fun getUsers(
        @Header("Authorization") token: String
    ) : Call<FiwareResponseUser>

    companion object {
        private val BASE_URL: String = "http://192.168.0.16:3000/api/v1/"

        var okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        operator fun invoke(): FiwareAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FiwareAPI::class.java)
        }
    }
}

object user {

}

