package com.pf.aforo.data.model

class DataUser (private var type: String,
                private var id: String,
                private var firstName: String,
                private var lastName: String,
                private var email: String,
                private var phoneNumber: String,
                private var refOrganization: String,
                private var password: String,
                private var role: String,
                private var organizationOwner: Boolean,
                private var refBranchOffice: Boolean) {

    fun getType() : String {
        return type
    }

    fun getId() : String {
        return id
    }

    fun getFirstName() : String {
        return firstName
    }

    fun getLastName() : String {
        return lastName
    }

    fun getEmail() : String {
        return email
    }

    fun getPhoneNumber() : String {
        return phoneNumber
    }

    fun getRefOrganization() : String {
        return refOrganization
    }

    fun getPassword() : String {
        return password
    }

    fun getRole() : String {
        return role
    }

    fun getOrganizationOwner() : Boolean {
        return organizationOwner
    }

    fun refBranchOffice() : Boolean {
        return refBranchOffice
    }
}
