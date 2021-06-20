package com.pf.aforo.data.response

import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataRemoveCivilServant

data class FiwareResponseRemoveCivilServantFromBranchOffice(val data: DataRemoveCivilServant, val detail: String, val code: String) {
}