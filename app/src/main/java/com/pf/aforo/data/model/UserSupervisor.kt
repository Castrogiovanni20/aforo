package com.pf.aforo.data.model

class UserSupervisor ( var firstName: String,
                       var lastName: String,
                       var email: String,
                       var phoneNumber: String,
                       var refOrganization: String,
                       var password: String,
                       var passwordConfirm: String,
                       var role: String,
                       var settings: Settings) {

    fun isFirstNameLengthValid (): Boolean = (firstName.length in 2..60)

    fun isFirstNameAlphabetic (): Boolean = isLetters(firstName)

    fun isLastNameLengthValid (): Boolean = (lastName.length in 2..60)

    fun isLastNameAlphabetic (): Boolean = isLetters(lastName)

    fun isEmailValid (): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isrefOrganizationValid (): Boolean = (refOrganization.length in 3..40)

    fun isPasswordValid (): Boolean = (password.length in 8..100)

    fun arePasswordsEquals (): Boolean = (password == passwordConfirm)

    fun isPhoneNumberValid (): Boolean = phoneNumber.length in 6..15

    fun isLetters(string: String): Boolean {
        return string.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?\$".toRegex())
    }


}