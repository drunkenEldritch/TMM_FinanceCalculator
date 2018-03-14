package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity

@Dao
abstract class  DateDao: BaseDao<DateEntity>(){

    @Query("SELECT * FROM ${DateEntity.TABLE_NAME}")
    abstract fun getAll():LiveData<List<DateEntity>>

    @Query("DELETE FROM ${DateEntity.TABLE_NAME}")
    abstract fun deleteAll()
}