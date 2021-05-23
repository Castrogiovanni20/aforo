package com.pf.aforo.data.model

data class BranchOffice(val type: String = "",
                        val id: String = "",
                        val refOrganization: String,
                        val name: String,
                        val description: String,
                        val official: String,
                        val maxCapacity: String) {
}