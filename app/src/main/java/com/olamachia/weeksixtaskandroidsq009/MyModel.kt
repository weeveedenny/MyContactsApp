package com.olamachia.weeksixtaskandroidsq009

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.Exclude

data class MyModel(
    var id: String? = null,
    val name: String? = null,
    val phone: String? = null
):Parcelable {
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

    companion object CREATOR : Parcelable.Creator<MyModel> {
        override fun createFromParcel(parcel: Parcel): MyModel {
            return MyModel(parcel)
        }

        override fun newArray(size: Int): Array<MyModel?> {
            return arrayOfNulls(size)
        }
    }
}