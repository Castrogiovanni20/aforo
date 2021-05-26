package com.pf.aforo.data.model

data class BranchOffice(val type: String = "",
                        val id: String = "",
                        val refOrganization: String,
                        val name: String,
                        val description: String,
                        val refUser: String,
                        val width: Int,
                        val length: Int,
                        val maxCapacity: Int) {
}