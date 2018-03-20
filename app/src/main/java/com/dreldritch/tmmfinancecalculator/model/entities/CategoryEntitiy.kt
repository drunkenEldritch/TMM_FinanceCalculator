package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = CategoryEntitiy.TABLE_NAME, indices = [Index(value = ["category"], unique = true)])
data class CategoryEntitiy(
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

    companion object CREATOR : Parcelable.Creator<CategoryEntitiy> {

        const val TABLE_NAME = "Categories"

        override fun createFromParcel(parcel: Parcel): CategoryEntitiy {
            return CategoryEntitiy(parcel)
        }

        override fun newArray(size: Int): Array<CategoryEntitiy?> {
            return arrayOfNulls(size)
        }
    }
}