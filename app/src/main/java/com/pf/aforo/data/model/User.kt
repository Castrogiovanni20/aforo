package com.pf.aforo.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class User(private var email: String, private var password: String) {
    val isDataValid: Boolean = false;

    fun getEmail() : String {
        return email
    }

    fun getPassword() : String {
        return password
    }

    fun setEmail(email: String) {
        this.email = email;
    }

    fun setPassword(password: String) {
        this.password = password;
    }
}
