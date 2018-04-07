package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TransactionEntity.TABLE_NAME)
data class TransactionEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "price") var price: Double,
        @ColumnInfo(name = "description") var description: String,
        @ColumnInfo(name = "in_out") var in_out: Int,
        @ColumnInfo(name = "date_id") var dateId: Long,
        @ColumnInfo(name = "category_id") var categoryId: Long?,
        @ColumnInfo(name = "account_id") var accountId: Long)
    :Parcelable{
    companion object {
        const val TABLE_NAME = "Entries"
    }
}