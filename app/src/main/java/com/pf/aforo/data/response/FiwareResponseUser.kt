package com.pf.aforo.data.response

import com.pf.aforo.data.model.Data
import com.pf.aforo.data.model.DataUser

class FiwareResponseUser(private var data: Array<DataUser>, private var detail: String, private var code: String) {
    fun getDetail() : String {
        return detail
    }

    fun getCode() : String {
        return code
    }

    fun getData() : Array<DataUser> {
        return data
    }

}
