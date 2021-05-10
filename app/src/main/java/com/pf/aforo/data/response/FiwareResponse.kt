package com.pf.aforo.data.response

import com.pf.aforo.data.model.Data

class FiwareResponse(private var data: Data, private var detail: String, private var code: String) {
    fun getDetail() : String {
        return detail
    }

    fun getCode() : String {
        return code
    }

    fun getData() : Data {
        return data
    }

}
