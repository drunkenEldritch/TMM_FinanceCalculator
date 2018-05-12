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

    @Query("SELECT id FROM Dates WHERE date = :date")
    abstract fun getDateId(date: String): Long

    @Query("SELECT * FROM Dates WHERE id = :id")
    abstract fun getDateById(id: Long): LiveData<DateEntity>
}