package com.pf.aforo.data.model

import android.os.Parcel
import android.os.Parcelable

data class BranchOffice(
    val type: String,
    val id: String,
    val refOrganization: String,
    val name: String,
    val description: String,
    val refUser: String,
    val currentCapacity: Int,
    val width: Int,
    val length: Int,
    val maxCapacity: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(id)
        parcel.writeString(refOrganization)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(refUser)
        parcel.writeInt(currentCapacity)
        parcel.writeInt(width)
        parcel.writeInt(length)
        parcel.writeInt(maxCapacity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BranchOffice> {
        override fun createFromParcel(parcel: Parcel): BranchOffice {
            return BranchOffice(parcel)
        }

        override fun newArray(size: Int): Array<BranchOffice?> {
            return arrayOfNulls(size)
        }
    }
}