package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = CategoryEntity.TABLE_NAME, indices = [Index(value = ["category"], unique = true)])
data class CategoryEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo(name = "category") var category: String,
        @ColumnInfo(name = "icon_color") var iconColor: Int): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(category)
        parcel.writeInt(iconColor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryEntity> {

        const val TABLE_NAME = "Categories"

        override fun createFromParcel(parcel: Parcel): CategoryEntity {
            return CategoryEntity(parcel)
        }

        override fun newArray(size: Int): Array<CategoryEntity?> {
            return arrayOfNulls(size)
        }
    }
}