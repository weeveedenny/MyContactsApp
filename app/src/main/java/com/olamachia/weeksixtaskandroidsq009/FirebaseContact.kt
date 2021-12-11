package com.olamachia.weeksixtaskandroidsq009

import android.os.Parcel
import android.os.Parcelable

data class FirebaseContact(
    var id: String? = null,
    val name: String? = null,
    val phone: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FirebaseContact> {
        override fun createFromParcel(parcel: Parcel): FirebaseContact {
            return FirebaseContact(parcel)
        }

        override fun newArray(size: Int): Array<FirebaseContact?> {
            return arrayOfNulls(size)
        }
    }
}