package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = DateEntity.TABLE_NAME, indices = [Index(value = ["date"], unique = true)])
data class DateEntity(
        @PrimaryKey(autoGenerate = true) val id: Long,
        @ColumnInfo(name = "date") var date: String)
{
    companion object {
        const val TABLE_NAME = "Dates"
    }
}