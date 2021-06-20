package com.pf.aforo.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pf.aforo.data.model.DataUser
import com.pf.aforo.data.model.UserFuncionario
import com.pf.aforo.data.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UsersRepository()
{
    var addUserSuccessResponseLiveData = MutableLiveData<String>()
    var addUserFailureResponseLiveData = MutableLiveData<String>()

    var getUserResponseLiveData = MutableLiveData<DataUser>()
    var getUserFailureResponseLiveData = MutableLiveData<String>()

    var getUsersSuccessResponseLiveData = MutableLiveData<Array<DataUser>>()
    var getUsersFailureResponseLiveData = MutableLiveData<String>()

    var deleteUserSuccessResponseLiveData = MutableLiveData<String>()
    var deleteUserFailureResponseLiveData = MutableLiveData<String>()

    var updateUserSuccessResponseLiveData = MutableLiveData<String>()
    var updateUserFailureResponseLiveData = MutableLiveData<String>()

    var updateUserRoleSuccessResponseLiveData = MutableLiveData<String>()
    var updateUserRoleFailureResponseLiveData = MutableLiveData<String>()

    private val SERVER_ERROR_MSG: String = "Estamos teniendo problemas con el servidor. Intente de nuevo más tarde."

    fun addUser(token: String, user: UserFuncionario){
        FiwareAPI().addUser(token, user)
            .enqueue(object: Callback<FiwareResponse>{
                override fun onFailure(call: Call<FiwareResponse>?, t: Throwable?) {
                    if (t != null) {
                        addUserFailureResponseLiveData.value = SERVER_ERROR_MSG
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
            .enqueue(object: Callback<FiwareResponseGetUser>{
                override fun onFailure(call: Call<FiwareResponseGetUser>?, t: Throwable?) {
                    if (t != null) {
                        getUserFailureResponseLiveData.value = SERVER_ERROR_MSG
                        Log.d("ApiGetUser", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseGetUser>?, fiwareResponse: Response<FiwareResponseGetUser>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                getUserResponseLiveData.value = fiwareResponse.body().data
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
            .enqueue(object: Callback<FiwareResponseGetUsers>{
                override fun onFailure(call: Call<FiwareResponseGetUsers>?, t: Throwable?) {
                    if (t != null) {
                        getUsersFailureResponseLiveData.value = SERVER_ERROR_MSG
                        Log.d("ApiGetUsers", "Fallo el request" + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseGetUsers>?, fiwareResponse: Response<FiwareResponseGetUsers>?) {
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
                        deleteUserFailureResponseLiveData.value = SERVER_ERROR_MSG
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
                            deleteUserFailureResponseLiveData.value = fiwareResponseUser.errorBody().string()
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
                        updateUserFailureResponseLiveData.value = SERVER_ERROR_MSG
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
                        updateUserRoleFailureResponseLiveData.value = SERVER_ERROR_MSG
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