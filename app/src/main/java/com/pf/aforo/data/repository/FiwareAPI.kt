package com.pf.aforo.data.repository

import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.data.model.UserSupervisor
import com.pf.aforo.data.response.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface FiwareAPI {

    /**
     * @description Authentication endpoints
     */
    @POST("authentication/signin")
    fun login(
        @Body user: UserLogin
    ) : Call<FiwareResponse>

    @POST("authentication/signup")
    fun register(
        @Body user: UserSupervisor
    ) : Call<FiwareResponse>


    /**
     * @description Users endpoints
     */
    @POST("users")
    fun addUser(
        @Header("Authorization") token: String,
        @Body user: UserFuncionario
    ) : Call<FiwareResponse>

    @GET("users/{id}")
    fun getUser(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Call<FiwareResponseUser>

    @GET("users/organization")
    fun getUsers(
        @Header("Authorization") token: String
    ) : Call<FiwareResponseUser>

    @DELETE("users/{id}")
    fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Call<FiwareResponseDeleteUser>

    @PUT("users/{id}")
    fun updateUser(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body user: UserFuncionario
    ) : Call<FiwareResponseUserFuncionario>

    @PUT("users/add-role/{entityId}/{role}")
    fun updateUserRole(
        @Header("Authorization") token: String,
        @Path("entityId") entityId: String,
        @Path("role") role: String
    ) : Call<FiwareResponseEditUserRole>


    /**
     * @description Branch Offices endpoints
     */
    @POST("branch-offices")
    fun addBranchOffice(
        @Header("Authorization") token: String,
        @Body branchOffice: BranchOffice
    ) : Call<FiwareResponseBranchOffice>

    @GET("branch-office")
    fun getBranchOffices(
        @Header("Authorization") token: String
    ) : Call<FiwareResponseGetBranchOffice>

    @PUT("branch-offices/{entityId}")
    fun updateBranchOffice(
        @Header("Authorization") token: String,
        @Path("entityId") entityId: String
    ) : Call<FiwareResponseEditBranchOffice>

    @DELETE("branch-offices/{entityId}")
    fun deleteBranchOffice(
        @Header("Authorization") token: String,
        @Path("entityId") entityId: String
    ) : Call<FiwareResponseDeleteBranchOffice>

    companion object {
        //private val BASE_URL: String = "http://192.168.0.222:3000/api/v1/"
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

