package com.namnp.androidjetpack.navigation.onboarding

import android.os.Parcel
import android.os.Parcelable

data class ParcelableDataArgs(val data1:Int): Parcelable {
    var data2: Long=1
    var data3: String?=null
    var data4: Int=1

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        data2 = parcel.readLong()
        data3 = parcel.readString()
        data4 = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(data1)
        parcel.writeLong(data2)
        parcel.writeString(data3)
        parcel.writeInt(data4)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelableDataArgs> {
        override fun createFromParcel(parcel: Parcel): ParcelableDataArgs {
            return ParcelableDataArgs(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableDataArgs?> {
            return arrayOfNulls(size)
        }
    }
}