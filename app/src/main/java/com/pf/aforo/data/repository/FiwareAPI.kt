package com.pf.aforo.data.repository

import com.pf.aforo.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FiwareAPI {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body user: User): Call<User>
}