package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.persistence.room.*

@Dao
abstract class BaseDao<T>{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(obj: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(obj: T)

    @Delete
    abstract fun delete(obj: T)
}