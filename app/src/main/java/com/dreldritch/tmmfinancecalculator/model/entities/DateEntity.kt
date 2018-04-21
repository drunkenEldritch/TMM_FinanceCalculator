package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.*
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = DateEntity.TABLE_NAME,
        indices = [Index(value = ["date"], unique = true)])
data class DateEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo(name = "date") var date: String) : Parcelable {

    companion object {
        const val TABLE_NAME = "Dates"
    }
}