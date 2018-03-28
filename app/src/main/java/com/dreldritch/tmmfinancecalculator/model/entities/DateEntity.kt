package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = DateEntity.TABLE_NAME, indices = [Index(value = ["date"], unique = true)])
data class DateEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo(name = "date") var date: String)
    :Parcelable
{
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DateEntity> {
        const val TABLE_NAME = "Dates"
        override fun createFromParcel(parcel: Parcel): DateEntity {
            return DateEntity(parcel)
        }

        override fun newArray(size: Int): Array<DateEntity?> {
            return arrayOfNulls(size)
        }
    }
}