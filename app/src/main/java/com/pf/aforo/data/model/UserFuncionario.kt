package com.pf.aforo.data.model

import android.text.Editable
import java.io.Serializable

class UserFuncionario(
    var id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var password: String,
    var role: String ) : Serializable {


    fun isFirstNameLengthValid (): Boolean = (firstName.length in 2..60)

    fun isFirstNameAlphabetic (): Boolean = isLetters(firstName)

    fun isLastNameLengthValid (): Boolean = (lastName.length in 2..60)

    fun isLastNameAlphabetic (): Boolean = isLetters(lastName)

    fun isEmailValid (): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid (): Boolean = (password.length in 8..100)

    fun isPhoneNumberValid (): Boolean = (!phoneNumber.isNotEmpty() || phoneNumber.length in 1..15)

    fun isLetters(string: String): Boolean {
        return string.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?\$".toRegex())
    }
}