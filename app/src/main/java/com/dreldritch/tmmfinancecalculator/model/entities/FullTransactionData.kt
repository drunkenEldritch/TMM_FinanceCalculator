package com.dreldritch.tmmfinancecalculator.model.entities

import android.os.Parcel
import android.os.Parcelable

class FullTransactionData(
        val id: Long?, val name: String,
        val price: Double,
        val description: String,
        val in_out: Int,
        val date_id: Long, val date: String,
        val account_id: Long, val account: String,
        val category_id: Long?, val category: String?)
    : Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeString(description)
        parcel.writeInt(in_out)
        parcel.writeLong(date_id)
        parcel.writeString(date)
        parcel.writeLong(account_id)
        parcel.writeString(account)
        parcel.writeValue(category_id)
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FullTransactionData> {
        override fun createFromParcel(parcel: Parcel): FullTransactionData {
            return FullTransactionData(parcel)
        }

        override fun newArray(size: Int): Array<FullTransactionData?> {
            return arrayOfNulls(size)
        }
    }

}