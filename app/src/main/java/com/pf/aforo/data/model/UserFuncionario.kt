package com.pf.aforo.data.model

class UserFuncionario (private var firstName: String,
                       private var lastName: String,
                       private var email: String,
                       private var phoneNumber: String,
                       private var refOrganization: String,
                       private var password: String,
                       private var role: String,
                       private var token: String) {

    fun getFirstName () : String {
        return firstName
    }

    fun getLastName () : String {
        return lastName
    }

    fun getEmail () : String {
        return email
    }

    fun getPhoneNumber () : String {
        return phoneNumber
    }

    fun getRefOrganization () : String {
        return refOrganization
    }

    fun getPassword () : String {
        return password
    }

    fun getRole () : String {
        return role
    }

    fun getToken () : String {
        return token
    }
}