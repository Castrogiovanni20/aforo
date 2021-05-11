package com.pf.aforo.data.model

class User (private var fullName: String, private var email: String, private var phoneNumber: Int, private var organization: String, private var password: String) {

    fun getFullName () : String {
        return fullName
    }

    fun getEmail () : String {
        return email
    }

    fun getPhoneNumber () : Int {
        return phoneNumber
    }

    fun getOrganization () : String {
        return organization
    }

    fun getPassword () : String {
        return password
    }


}