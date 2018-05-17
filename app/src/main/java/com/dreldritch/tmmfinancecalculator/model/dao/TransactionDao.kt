package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.TransactionEntity

@Dao
abstract class  TransactionDao: BaseDao<TransactionEntity>(){

    @Query("SELECT * FROM ${TransactionEntity.TABLE_NAME}")
    abstract fun getAll():LiveData<List<TransactionEntity>>

    @Query("DELETE FROM ${TransactionEntity.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("DELETE FROM Transactions WHERE id = :id")
    abstract fun deleteById(id: Long)

    @Query("SELECT COUNT(*) FROM Transactions WHERE date_id = :date_id")
    abstract fun checkForLastDate(date_id: Long):Int
}