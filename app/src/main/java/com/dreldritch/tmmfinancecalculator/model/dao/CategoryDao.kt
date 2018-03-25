package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity

@Dao
abstract class  CategoryDao: BaseDao<CategoryEntity>(){

    @Query("SELECT * FROM ${CategoryEntity.TABLE_NAME}")
    abstract fun getAll():LiveData<List<CategoryEntity>>

    @Query("DELETE FROM ${CategoryEntity.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("SELECT id FROM ${CategoryEntity.TABLE_NAME} WHERE category = :category")
    abstract fun getCategoryId(category: String): Long
}