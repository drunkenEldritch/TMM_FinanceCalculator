package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity

@Dao
abstract class  AccountDao: BaseDao<AccountEntity>(){

    @Query("SELECT * FROM ${AccountEntity.TABLE_NAME}")
    abstract fun getAll():LiveData<List<AccountEntity>>

    @Query("DELETE FROM ${AccountEntity.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("SELECT id FROM ${AccountEntity.TABLE_NAME} WHERE account = :account")
    abstract fun getAccountId(account: String): Long
}