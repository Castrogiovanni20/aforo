package com.pf.aforo.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
data class UserFuncionario(val id: String,
                           var firstName: String,
                           var lastName: String,
                           var email: String,
                           var identificationNumber: String?,
                           var phoneNumber: String,
                           var password: String,
                           var passwordConfirm: String,
                           var role: String,
                           var refBranchOffice: String?,
                           var userDeviceToken: String?,
                           var refOrganization: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: ""
    ) {
    }

    fun isFirstNameLengthValid (): Boolean = (firstName.length in 2..60)

    fun isFirstNameAlphabetic (): Boolean = isLetters(firstName)

    fun isLastNameLengthValid (): Boolean = (lastName.length in 2..60)

    fun isLastNameAlphabetic (): Boolean = isLetters(lastName)

    fun isEmailValid (): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid (): Boolean = (password.length in 8..100)

    fun isPhoneNumberValid (): Boolean = phoneNumber.length in 6..15

    fun isIdentificationNumberValid (): Boolean = identificationNumber?.length == 8

    fun arePasswordsEquals (): Boolean = (password == passwordConfirm)

    fun isLetters(string: String): Boolean {
        return string.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?\$".toRegex())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeString(password)
        parcel.writeString(role)
        parcel.writeString(userDeviceToken)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserFuncionario> {
        override fun createFromParcel(parcel: Parcel): UserFuncionario {
            return UserFuncionario(parcel)
        }

        override fun newArray(size: Int): Array<UserFuncionario?> {
            return arrayOfNulls(size)
        }
    }
}