package com.pf.aforo.data.response

import com.pf.aforo.data.model.DataUser

data class FiwareResponseGetUser(var data: DataUser, var detail: String, var code: String)  {
}