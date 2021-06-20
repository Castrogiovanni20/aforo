package com.pf.aforo.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataBranchOfficeHistory
import com.pf.aforo.data.response.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

class BranchOfficesRepository {

    var addBranchOfficeSuccessResponseLiveData = MutableLiveData<BranchOffice>()
    var addBranchOfficeFailureResponseLiveData = MutableLiveData<String>()

    var getBranchOfficeSuccessResponseLiveData = MutableLiveData<ArrayList<BranchOffice>>()
    var getBranchOfficesFailureResponseLiveData = MutableLiveData<String>()

    var updateBranchOfficeSuccessResponseLiveData = MutableLiveData<String>()
    var updateBranchOfficeFailureResponseLiveData = MutableLiveData<String>()

    var deleteBranchOfficeSuccessResponseLiveData = MutableLiveData<String>()
    var deleteBranchOfficeFailureResponseLiveData = MutableLiveData<String>()

    var assignCivilServantSuccessResponseLiveData = MutableLiveData<BranchOffice>()
    var assignCivilServantFailureResponseLiveData = MutableLiveData<String>()

    var removeCivilServantSuccessResponseLiveData = MutableLiveData<String>()
    var removeCivilServantFailureResponseLiveData = MutableLiveData<String>()

    var getBranchOfficeByIdSuccessResponseLiveData = MutableLiveData<BranchOffice>()
    var getBranchOfficeByIdFailureResponseLiveData = MutableLiveData<String>()

    var getBranchOfficeHistorySuccessResponseLiveData = MutableLiveData<Array<DataBranchOfficeHistory>>()
    var getBranchOfficeHistoryFailureResponseLiveData = MutableLiveData<String>()

    private val SERVER_ERROR_MSG: String = "Estamos teniendo problemas con el servidor. Intente de nuevo más tarde."

    fun addBranchOffice(token: String, branchOffice: BranchOffice) {
        FiwareAPI().addBranchOffice(token, branchOffice)
            .enqueue(object: retrofit2.Callback<FiwareResponseBranchOffice>{
                override fun onFailure(call: Call<FiwareResponseBranchOffice>?, t: Throwable?) {
                    if (t != null) {
                        addBranchOfficeFailureResponseLiveData.value = SERVER_ERROR_MSG
                        Log.d("ApiAddBranchOffice", "Fallo el request: " + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseBranchOffice>?, fiwareResponse: Response<FiwareResponseBranchOffice>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                addBranchOfficeSuccessResponseLiveData.value = fiwareResponse.body().data
                                Log.d("ApiAddBranchOffice", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            addBranchOfficeFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiAddBranchOffice", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun getBranchOffices(token: String){
        FiwareAPI().getBranchOffices(token)
            .enqueue(object: retrofit2.Callback<FiwareResponseGetBranchOffice>{
                override fun onFailure(call: Call<FiwareResponseGetBranchOffice>?, t: Throwable?) {
                    if (t != null) {
                        getBranchOfficesFailureResponseLiveData.value = SERVER_ERROR_MSG
                        Log.d("ApiGetBranchOffice", "Fallo el request: " + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseGetBranchOffice>?, fiwareResponse: Response<FiwareResponseGetBranchOffice>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                getBranchOfficeSuccessResponseLiveData.value = fiwareResponse.body().data
                                Log.d("ApiGetBranchOffice", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            getBranchOfficesFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiGetBranchOffice", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun updateBranchOffice(token: String, id: String, branchOffice: BranchOffice){
        FiwareAPI().updateBranchOffice(token, id, branchOffice)
            .enqueue(object: retrofit2.Callback<FiwareResponseEditBranchOffice>{
                override fun onFailure(call: Call<FiwareResponseEditBranchOffice>?, t: Throwable?) {
                    if (t != null) {
                        updateBranchOfficeFailureResponseLiveData.value = SERVER_ERROR_MSG
                        Log.d("ApiUpdateBranchOffice", "Fallo el request: " + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseEditBranchOffice>?, fiwareResponse: Response<FiwareResponseEditBranchOffice>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                updateBranchOfficeSuccessResponseLiveData.value = fiwareResponse.code().toString()
                                Log.d("ApiUpdateBranchOffice", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            updateBranchOfficeFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiUpdateBranchOffice", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun deleteBranchOffice(token: String, id: String){
        FiwareAPI().deleteBranchOffice(token, id)
            .enqueue(object: retrofit2.Callback<FiwareResponseDeleteBranchOffice>{
                override fun onFailure(call: Call<FiwareResponseDeleteBranchOffice>?, t: Throwable?) {
                    if (t != null) {
                        deleteBranchOfficeFailureResponseLiveData.value = SERVER_ERROR_MSG
                        Log.d("ApiDeleteBranchOffice", "Fallo el request: " + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseDeleteBranchOffice>?, fiwareResponse: Response<FiwareResponseDeleteBranchOffice>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                deleteBranchOfficeSuccessResponseLiveData.value = fiwareResponse.code().toString()
                                Log.d("ApiDeleteBranchOffice", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            deleteBranchOfficeFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiDeleteBranchOffice", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun assignCivilServant(token: String, entityId: String, refUser: String?) {
        FiwareAPI().assignCivilServantToBranchOffice(token, entityId, refUser)
            .enqueue(object: retrofit2.Callback<FiwareResponseAssignCivilServantToBranchOffice>{
                override fun onFailure(call: Call<FiwareResponseAssignCivilServantToBranchOffice>?, t: Throwable?) {
                    if (t != null) {
                        assignCivilServantFailureResponseLiveData.value = SERVER_ERROR_MSG
                        Log.d("ApiAssignCivilServant", "Fallo el request: " + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseAssignCivilServantToBranchOffice>?, fiwareResponse: Response<FiwareResponseAssignCivilServantToBranchOffice>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                assignCivilServantSuccessResponseLiveData.value = fiwareResponse.body().data
                                Log.d("ApiAssignCivilServant", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            assignCivilServantFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiAssignCivilServant", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun removeCivilServant(token: String, entityId: String, refUser: String?) {
        FiwareAPI().removeCivilServantFromBranchOffice(token, entityId, refUser)
            .enqueue(object: retrofit2.Callback<FiwareResponseRemoveCivilServantFromBranchOffice>{
                override fun onFailure(call: Call<FiwareResponseRemoveCivilServantFromBranchOffice>?, t: Throwable?) {
                    if (t != null) {
                        removeCivilServantFailureResponseLiveData.value = SERVER_ERROR_MSG
                        Log.d("ApiRemoveCivilServant", "Fallo el request: " + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseRemoveCivilServantFromBranchOffice>?, fiwareResponse: Response<FiwareResponseRemoveCivilServantFromBranchOffice>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                removeCivilServantSuccessResponseLiveData.value = fiwareResponse.body().code
                                Log.d("ApiRemoveCivilServant", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            removeCivilServantFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiRemoveCivilServant", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun getBranchOfficeById(token: String, id: String){
        FiwareAPI().getBranchOfficeById(token, id)
            .enqueue(object: retrofit2.Callback<FiwareResponseBranchOffice>{
                override fun onFailure(call: Call<FiwareResponseBranchOffice>?, t: Throwable?) {
                    if (t != null) {
                        getBranchOfficeByIdFailureResponseLiveData.value = SERVER_ERROR_MSG
                        Log.d("ApiGetBranchOfficeById", "Fallo el request: " + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseBranchOffice>?, fiwareResponse: Response<FiwareResponseBranchOffice>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                getBranchOfficeByIdSuccessResponseLiveData.value = fiwareResponse.body().data
                                Log.d("ApiGetBranchOfficeById", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            getBranchOfficeByIdFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiGetBranchOfficeById", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

    fun getBranchOfficeHistory(token: String, id: String){
        FiwareAPI().getBranchOfficeHistory(token, id)
            .enqueue(object: retrofit2.Callback<FiwareResponseBranchOfficeHistory>{
                override fun onFailure(call: Call<FiwareResponseBranchOfficeHistory>?, t: Throwable?) {
                    if (t != null) {
                        getBranchOfficeHistoryFailureResponseLiveData.value = SERVER_ERROR_MSG
                        Log.d("ApiGetBranchOfficeById", "Fallo el request: " + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseBranchOfficeHistory>?, fiwareResponse: Response<FiwareResponseBranchOfficeHistory>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                getBranchOfficeHistorySuccessResponseLiveData.value = fiwareResponse.body().data
                                Log.d("ApiGetBranchOfficeById", "API response: " + fiwareResponse.code().toString())
                            }
                        } else {
                            getBranchOfficeHistoryFailureResponseLiveData.value = fiwareResponse.code().toString()
                            Log.d("ApiGetBranchOfficeById", "API response: " + fiwareResponse.code().toString())
                        }
                    }
                }

            })
    }

}