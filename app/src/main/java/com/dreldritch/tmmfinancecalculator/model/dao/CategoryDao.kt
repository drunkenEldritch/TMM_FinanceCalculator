package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity

@Dao
abstract class  CategoryDao: BaseDao<CategoryEntity>(){

    @Query("SELECT * FROM Categories")
    abstract fun getAll():LiveData<List<CategoryEntity>>

    @Query("DELETE FROM Categories")
    abstract fun deleteAll()

    @Query("SELECT id FROM Categories WHERE category = :category")
    abstract fun getCategoryId(category: String):LiveData<Long>

    @Query("SELECT * FROM Categories WHERE category = :category")
    abstract fun getCategoryEntity(category: String):LiveData<CategoryEntity>
}