package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = CategoryEntitiy.TABLE_NAME, indices = [Index(value = ["category"], unique = true)])
data class CategoryEntitiy(
        @PrimaryKey(autoGenerate = true) val id: Long,
        @ColumnInfo(name = "category") var category: String)
{
    companion object {
        const val TABLE_NAME = "Categories"
    }
}