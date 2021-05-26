package com.pf.aforo.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.response.FiwareResponseBranchOffice
import com.pf.aforo.data.response.FiwareResponseDeleteBranchOffice
import com.pf.aforo.data.response.FiwareResponseEditBranchOffice
import com.pf.aforo.data.response.FiwareResponseGetBranchOffice
import retrofit2.Call
import retrofit2.Response

class BranchOfficesRepository {

    var addBranchOfficeSuccessResponseLiveData = MutableLiveData<String>()
    var addBranchOfficeFailureResponseLiveData = MutableLiveData<String>()

    var getBranchOfficeSuccessResponseLiveData = MutableLiveData<ArrayList<BranchOffice>>()
    var getBranchOfficesFailureResponseLiveData = MutableLiveData<String>()

    var updateBranchOfficeSuccessResponseLiveData = MutableLiveData<String>()
    var updateBranchOfficeFailureResponseLiveData = MutableLiveData<String>()

    var deleteBranchOfficeSuccessResponseLiveData = MutableLiveData<String>()
    var deleteBranchOfficeFailureResponseLiveData = MutableLiveData<String>()

    fun addBranchOffice(token: String, branchOffice: BranchOffice) {
        FiwareAPI().addBranchOffice(token, branchOffice)
            .enqueue(object: retrofit2.Callback<FiwareResponseBranchOffice>{
                override fun onFailure(call: Call<FiwareResponseBranchOffice>?, t: Throwable?) {
                    if (t != null) {
                        addBranchOfficeFailureResponseLiveData.value = "404"
                        Log.d("ApiAddBranchOffice", "Fallo el request: " + t.message)
                    }
                }
                override fun onResponse(call: Call<FiwareResponseBranchOffice>?, fiwareResponse: Response<FiwareResponseBranchOffice>?) {
                    if (fiwareResponse != null) {
                        if (fiwareResponse.isSuccessful) {
                            if (fiwareResponse.body().code == "SUCCESS") {
                                addBranchOfficeSuccessResponseLiveData.value = fiwareResponse.code().toString()
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
                        getBranchOfficesFailureResponseLiveData.value = "404"
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

    fun updateBranchOffice(token: String, id: String){
        FiwareAPI().updateBranchOffice(token, id)
            .enqueue(object: retrofit2.Callback<FiwareResponseEditBranchOffice>{
                override fun onFailure(call: Call<FiwareResponseEditBranchOffice>?, t: Throwable?) {
                    if (t != null) {
                        updateBranchOfficeFailureResponseLiveData.value = "404"
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
                        deleteBranchOfficeFailureResponseLiveData.value = "404"
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

}