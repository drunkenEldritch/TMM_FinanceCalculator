package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dreldritch.tmmfinancecalculator.model.entities.*

@Dao
abstract class FullTransactionDao {
    @Query ("SELECT * FROM ${EntryEntity.TABLE_NAME} " +
            "JOIN ${DateEntity.TABLE_NAME} " +
            "JOIN ${AccountEntity.TABLE_NAME} " +
            "LEFT JOIN ${CategoryEntity.TABLE_NAME}")
    abstract fun getAllTransactions(): LiveData<List<FullTransactionData>>
}