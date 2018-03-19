package com.dreldritch.tmmfinancecalculator.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = AccountEntity.TABLE_NAME, indices = [Index(value = ["account"], unique = true)])
data class AccountEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo(name = "account") var account: String)
{
    companion object {
        const val TABLE_NAME = "Accounts"
    }
}