package com.pf.aforo.data.model

import com.pf.aforo.data.repository.user

class UserLogin (var username: String, var password: String) {

    fun isUserNameValid () : Boolean {
        return username.isNotEmpty()
    }

    fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    fun isPasswordValid () : Boolean {
        return password.isNotEmpty()
    }
}