package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.*
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.ForeignKey.SET_NULL


//TODO ForeignKey tests
@Parcelize
@Entity(tableName = TransactionEntity.TABLE_NAME,
        indices = [(Index(value = ["category_id"])),
            (Index(value = ["account_id"]))],
        foreignKeys = [
            ForeignKey(
                entity = AccountEntity::class,
                parentColumns = ["id"],
                childColumns = ["account_id"],
                onDelete = CASCADE),
            ForeignKey(
                    entity = CategoryEntity::class,
                    parentColumns = ["id"],
                    childColumns = ["category_id"],
                    onDelete = SET_NULL)])
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
        const val TABLE_NAME = "Transactions"
    }
}