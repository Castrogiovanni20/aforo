package com.pf.aforo.data.model

class UserLogin (private var username: String, private var password: String) {

    fun getUsername () : String {
        return username
    }

    fun getPassword () : String {
        return password
    }

    fun isUserNameValid () : Boolean {
        return getUsername().isNotEmpty()
    }

    fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(getUsername()).matches()
    }

    fun isPasswordValid () : Boolean {
        return getPassword().isNotEmpty()
    }
}