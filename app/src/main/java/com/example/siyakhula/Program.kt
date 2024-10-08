//package com.example.siyakhula
//
//data class Program(
//    var id: String = "",
//    var name: String = ""
//)
package com.example.siyakhula

import android.os.Parcel
import android.os.Parcelable

data class Program(
    var id: String = "",
    var name: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Program> {
        override fun createFromParcel(parcel: Parcel): Program {
            return Program(parcel)
        }

        override fun newArray(size: Int): Array<Program?> {
            return arrayOfNulls(size)
        }
    }
}
