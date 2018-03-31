package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = CategoryEntity.TABLE_NAME, indices = [Index(value = ["category"], unique = true)])
data class CategoryEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo(name = "category") var category: String,
        @ColumnInfo(name = "icon_color") var iconColor: Int)
    : Parcelable {

    companion object {
        const val TABLE_NAME = "Categories"
    }
}