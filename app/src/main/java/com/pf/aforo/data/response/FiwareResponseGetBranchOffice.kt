package com.pf.aforo.data.response

import com.pf.aforo.data.model.BranchOffice

data class FiwareResponseGetBranchOffice (val data: ArrayList<BranchOffice>,val detail: String, val code: String) {
}