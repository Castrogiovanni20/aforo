package com.pf.aforo.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.model.UserLogin
import com.pf.aforo.data.model.UserSupervisor
import com.pf.aforo.data.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UsersRepository()
{
    var addUserSuccessResponseLiveData = MutableLiveData<String>()
    var addUserFailureResponseLiveData = MutableLiveData<String>()

    var getUserSuccessResponseLiveData = MutableLiveData<String>()
    var getUserFailureResponseLiveData = MutableLiveData<String>()

    var getUsersSuccessResponseLiveData = MutableLiveData<Array<DataUser>>()
    var getUsersFailureResponseLiveData = MutableLiveData<String>()

    var deleteUserSuccessResponseLiveData = MutableLiveData<String>()
    var deleteUserFailureResponseLiveData = MutableLiveData<String>()

    var updateUserSuccessResponseLiveData = MutableLiveData<String>()
    var updateUserFailureResponseLiveData = MutableLiveData<String>()

    var updateUserRoleSuccessResponseLiveData = MutableLiveData<String>()
    var updateUserRoleFailureResponseLiveData = MutableLiveData<String>()


    fun addUser(token: String, user: UserFuncionario){
        FiwareAPI().addUser(token, user)
            .enqueue(object: Callback<FiwareResponse>{
                override fun onFailure(call: Call<FiwareResponse>?, t: Throwable?) {
                    if (t != null) {
                        addUserFailureResponseLiveData.value = "404"
                        Log.d("ApiAddUser", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponse>?, fiwareResponse: Response<FiwareResponse>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                addUserSuccessResponseLiveData.value = fiwareResponse.code().toString()
                                Log.d("ApiAddUser", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            addUserFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiAddUser", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun getUser(token: String, id: String){
        FiwareAPI().getUser(token, id)
            .enqueue(object: Callback<FiwareResponseUser>{
                override fun onFailure(call: Call<FiwareResponseUser>?, t: Throwable?) {
                    if (t != null) {
                        getUserFailureResponseLiveData.value = "404"
                        Log.d("ApiGetUser", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseUser>?, fiwareResponse: Response<FiwareResponseUser>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                getUserSuccessResponseLiveData.value = fiwareResponse.body().data[0].role
                                Log.d("ApiGetUser", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            getUserFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiGetUser", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun getUsers(token: String){
        FiwareAPI().getUsers(token)
            .enqueue(object: Callback<FiwareResponseUser>{
                override fun onFailure(call: Call<FiwareResponseUser>?, t: Throwable?) {
                    if (t != null) {
                        getUsersFailureResponseLiveData.value = "404"
                        Log.d("ApiGetUsers", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseUser>?, fiwareResponse: Response<FiwareResponseUser>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                var response = fiwareResponse.body().data
                                getUsersSuccessResponseLiveData.value = response
                                Log.d("ApiGetUsers", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            getUsersFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiGetUsers", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun deleteUser(token: String, id: String){
        FiwareAPI().deleteUser(token, id)
            .enqueue(object: Callback<FiwareResponseDeleteUser>{
                override fun onFailure(call: Call<FiwareResponseDeleteUser>?, t: Throwable?) {
                    if (t != null) {
                        deleteUserFailureResponseLiveData.value = "404"
                        Log.d("ApiDeleteUser", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseDeleteUser>?, fiwareResponseUser: Response<FiwareResponseDeleteUser>?) {
                    if (fiwareResponseUser != null) {
                        if (fiwareResponseUser.isSuccessful) {
                            if (fiwareResponseUser.body().code == "SUCCESS") {
                                deleteUserSuccessResponseLiveData.value = fiwareResponseUser.body().code
                                Log.d("ApiDeleteUser", "API response: " + fiwareResponseUser.code().toString())
                            }
                        } else {
                            deleteUserFailureResponseLiveData.value = fiwareResponseUser.code().toString()
                            Log.d("ApiDeleteUser", "API response: " + fiwareResponseUser.code().toString())
                        }
                    }
                }

            })
    }

    fun updateUser(token: String, id: String, user: UserFuncionario){
        FiwareAPI().updateUser(token, id, user)
            .enqueue(object: Callback<FiwareResponseUserFuncionario>{
                override fun onFailure(call: Call<FiwareResponseUserFuncionario>?, t: Throwable?) {
                    if (t != null) {
                        updateUserFailureResponseLiveData.value = "404"
                        Log.d("ApiUpdateUser", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseUserFuncionario>?, fiwareResponse: Response<FiwareResponseUserFuncionario>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                updateUserSuccessResponseLiveData.value = fiwareResponse.body().code
                                Log.d("ApiUpdateUser", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            updateUserFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiUpdateUser", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun updateUserRole(token: String, id: String, role: String){
        FiwareAPI().updateUserRole(token, id, role)
            .enqueue(object: Callback<FiwareResponseEditUserRole>{
                override fun onFailure(call: Call<FiwareResponseEditUserRole>?, t: Throwable?) {
                    if (t != null) {
                        updateUserRoleFailureResponseLiveData.value = "404"
                        Log.d("ApiUpdateUserRole", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseEditUserRole>?, fiwareResponse: Response<FiwareResponseEditUserRole>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                updateUserRoleSuccessResponseLiveData.value = fiwareResponse.body().code
                                Log.d("ApiUpdateUserRole", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            updateUserRoleFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiUpdateUserRole", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }
            })
    }


}