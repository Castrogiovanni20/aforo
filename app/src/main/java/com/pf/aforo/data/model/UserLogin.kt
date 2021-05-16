package com.pf.aforo.data.model

class UserLogin (private var userName: String, private var password: String) {

    fun getUserName () : String {
        return userName
    }

    fun getPassword () : String {
        return password
    }

    fun isUserNameValid () : Boolean {
        return getUserName().isNotEmpty()
    }

    fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(getUserName()).matches()
    }

    fun isPasswordValid () : Boolean {
        return getPassword().isNotEmpty()
    }
}