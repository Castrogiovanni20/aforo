package com.pf.aforo.data.model

class UserRegister (private var fullName: String,
                    private var email: String,
                    private var phoneNumber: Int,
                    private var organization: String,
                    private var password: String,
                    private var passwordConfirm: String) {

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

    fun getPasswordConfirm () : String {
        return passwordConfirm
    }

    fun isFullNameLengthValid (): Boolean = (getFullName().length in 6..16)

    fun isFullNameAlphabetic (): Boolean = isLetters(getFullName())

    fun isEmailLengthValid (): Boolean = (getEmail().length in 10..100)

    fun isEmailValid (): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()

    fun isPhoneNumberValid (): Boolean = getPhoneNumber() >= 8

    fun isOrganizationValid (): Boolean = (getOrganization().length in 2..100)

    fun isPasswordValid (): Boolean = (getPassword().length in 8..100)

    fun arePasswordsEquals (): Boolean = (getPassword() == getPasswordConfirm())

    fun isLetters(string: String): Boolean {
        return string.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?\$".toRegex())
    }


}