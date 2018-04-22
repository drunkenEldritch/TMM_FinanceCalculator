package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.*
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//TODO ForeignKey tests
@Parcelize
@Entity(tableName = TransactionEntity.TABLE_NAME,
        indices = [(Index(value = ["category_id"], unique = true)),
            (Index(value = ["account_id"], unique = true))],
        foreignKeys = [
            ForeignKey(entity = (CategoryEntity::class),
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("category_id"),
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(entity = (AccountEntity::class),
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("account_id"),
                    onDelete = ForeignKey.CASCADE)])
data class TransactionEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "price") var price: Double,
        @ColumnInfo(name = "description") var description: String,
        @ColumnInfo(name = "in_out") var in_out: Int,
        @ColumnInfo(name = "date_id") var dateId: Long,
        @ColumnInfo(name = "category_id") var categoryId: Long?,
        @ColumnInfo(name = "account_id") var accountId: Long)
    : Parcelable {
    companion object {
        const val TABLE_NAME = "Entries"
    }
}