package com.muhammadwaleed.i210438

import android.os.Parcel
import android.os.Parcelable

data class Mentorhome(
    var name: String? = null,
    var occupation: String? = null,
    var status: String? = null,
    var pricePerSession: String? = null,
    var isFavorite: Boolean = false,
    var imageUrl: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(occupation)
        parcel.writeString(status)
        parcel.writeString(pricePerSession)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mentorhome> {
        override fun createFromParcel(parcel: Parcel): Mentorhome {
            return Mentorhome(parcel)
        }

        override fun newArray(size: Int): Array<Mentorhome?> {
            return arrayOfNulls(size)
        }
    }
}
