package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Query
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.TransactionEntity

@Dao
abstract class  TransactionDao: BaseDao<TransactionEntity>(){

    @Query("SELECT * FROM ${TransactionEntity.TABLE_NAME}")
    abstract fun getAll():LiveData<List<TransactionEntity>>

    @Query("DELETE FROM ${TransactionEntity.TABLE_NAME}")
    abstract fun deleteAll()

}