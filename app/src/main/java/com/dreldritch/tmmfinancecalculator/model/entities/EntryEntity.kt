package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = EntryEntity.TABLE_NAME)
data class EntryEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "price") var price: Double,
        @ColumnInfo(name = "description") var description: String,
        @ColumnInfo(name = "in_out") var in_out: Int,
        @ColumnInfo(name = "date_id") var dateId: Long,
        @ColumnInfo(name = "category_id") var categoryId: Long,
        @ColumnInfo(name = "account_id") var accountId: Long)
{
    companion object {
        const val TABLE_NAME = "Entries"
    }
}