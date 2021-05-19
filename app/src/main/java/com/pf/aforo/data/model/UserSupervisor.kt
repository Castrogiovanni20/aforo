package com.pf.aforo.data.model

class UserSupervisor (private var firstName: String,
                      private var lastName: String,
                      private var email: String,
                      private var phoneNumber: String,
                      private var refOrganization: String,
                      private var password: String,
                      private var passwordConfirm: String,
                      private var role: String) {

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

    fun getrefOrganization () : String {
        return refOrganization
    }

    fun getPassword () : String {
        return password
    }

    fun getPasswordConfirm () : String {
        return passwordConfirm
    }

    fun getRole () : String {
        return role
    }

    fun isFirstNameLengthValid (): Boolean = (getFirstName().length in 2..60)

    fun isFirstNameAlphabetic (): Boolean = isLetters(getFirstName())

    fun isLastNameLengthValid (): Boolean = (getLastName().length in 2..60)

    fun isLastNameAlphabetic (): Boolean = isLetters(getLastName())

    fun isEmailValid (): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()

    fun isrefOrganizationValid (): Boolean = (getrefOrganization().length in 3..40)

    fun isPasswordValid (): Boolean = (getPassword().length in 8..100)

    fun arePasswordsEquals (): Boolean = (getPassword() == getPasswordConfirm())

    fun isLetters(string: String): Boolean {
        return string.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?\$".toRegex())
    }


}