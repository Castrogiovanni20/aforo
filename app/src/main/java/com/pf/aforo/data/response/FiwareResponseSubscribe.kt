package com.pf.aforo.data.response

import com.pf.aforo.data.model.DataSubscription

data class FiwareResponseSubscribe(val data: DataSubscription, val detail: String, val code: String) {
}