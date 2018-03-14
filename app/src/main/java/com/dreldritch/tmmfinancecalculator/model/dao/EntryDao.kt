package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dreldritch.tmmfinancecalculator.model.entities.EntryEntity

@Dao
abstract class  EntryDao: BaseDao<EntryEntity>(){

    @Query("SELECT * FROM ${EntryEntity.TABLE_NAME}")
    abstract fun getAll():LiveData<List<EntryEntity>>

    @Query("DELETE FROM ${EntryEntity.TABLE_NAME}")
    abstract fun deleteAll()
}