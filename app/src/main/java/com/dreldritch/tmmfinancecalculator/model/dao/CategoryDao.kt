package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntitiy

@Dao
abstract class  CategoryDao: BaseDao<CategoryEntitiy>(){

    @Query("SELECT * FROM ${CategoryEntitiy.TABLE_NAME}")
    abstract fun getAll():LiveData<List<CategoryEntitiy>>

    @Query("DELETE FROM ${CategoryEntitiy.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("SELECT id FROM ${CategoryEntitiy.TABLE_NAME} WHERE category = :category")
    abstract fun getCategoryId(category: String): Long
}