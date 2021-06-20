package com.pf.aforo.data.response

import com.pf.aforo.data.model.Settings

data class FiwareResponseUpdateSettings(val data: Settings, val detail: String, val code: String) {
}