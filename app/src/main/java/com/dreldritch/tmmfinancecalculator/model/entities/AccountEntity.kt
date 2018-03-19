package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = AccountEntity.TABLE_NAME, indices = [Index(value = ["account"], unique = true)])
data class AccountEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo(name = "account") var account: String)
    :Parcelable
{
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString())


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(account)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountEntity> {

        const val TABLE_NAME = "Accounts"

        override fun createFromParcel(parcel: Parcel): AccountEntity {
            return AccountEntity(parcel)
        }

        override fun newArray(size: Int): Array<AccountEntity?> {
            return arrayOfNulls(size)
        }
    }
}