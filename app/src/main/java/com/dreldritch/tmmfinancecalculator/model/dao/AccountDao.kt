package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity

@Dao
abstract class  AccountDao: BaseDao<AccountEntity>(){

    val table = "Accounts"

    @Query("SELECT * FROM Accounts")
    abstract fun getAll():LiveData<List<AccountEntity>>

    @Query("DELETE FROM Accounts")
    abstract fun deleteAll()

    @Query("SELECT id FROM Accounts WHERE account = :account")
    abstract fun getAccountId(account: String): Long

    @Query("SELECT * FROM Accounts WHERE id = :id")
    abstract fun findById(id: Long): LiveData<AccountEntity>
}