package com.dreldritch.tmmfinancecalculator.model

import android.app.Application
import android.os.AsyncTask
import com.dreldritch.tmmfinancecalculator.model.dao.AccountDao
import com.dreldritch.tmmfinancecalculator.model.dao.CategoryDao
import com.dreldritch.tmmfinancecalculator.model.dao.DateDao
import com.dreldritch.tmmfinancecalculator.model.dao.EntryDao
import com.dreldritch.tmmfinancecalculator.model.database.EntryDatabase
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntitiy
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.EntryDataObject
import com.dreldritch.tmmfinancecalculator.model.entities.EntryEntity

class EntryDbRepository(val application: Application){
    private val dateDao: DateDao
    private val categoryDao: CategoryDao
    private val accountDao: AccountDao
    private val entryDao: EntryDao

    init {
        val db = EntryDatabase.getDatabase(application)
        dateDao = db.getDateDao()
        categoryDao = db.getCategoryDao()
        accountDao = db.getAccountDao()
        entryDao = db.getEntryDao()
    }

    fun insertEntryObject(entry: EntryDataObject){
        InsertEntryDataAsyncTask(entryDao, dateDao).execute(entry)
    }

    fun insertCategory(category: CategoryEntitiy) {
        InsertCategoryAsyncTask(categoryDao).execute(category)
    }

    fun getAllAccounts() = accountDao.getAll()

    fun getAllCategories() = categoryDao.getAll()

    /*fun isDbCreated() = EntryDatabase.getDatabase(application).getDatabaseCreated()*/

    private class InsertEntryDataAsyncTask(val entryDao: EntryDao, val dateDao: DateDao) : AsyncTask<EntryDataObject, Void, Void>() {
        override fun doInBackground(vararg entry: EntryDataObject): Void? {

            var dateId = dateDao.insert(DateEntity(null, entry[0].date))
            if(dateId < 0) dateId = dateDao.getDateId(entry[0].date)

            val categoryId = if(entry[0].categoryEntitiy == null) null else entry[0].categoryEntitiy!!.id

            //1 = default account
            val accountId = if(entry[0].accountEntity == null) 1 else entry[0].accountEntity!!.id

            entryDao.insert(EntryEntity(null,
                    entry[0].name,
                    entry[0].price,
                    entry[0].description,
                    entry[0].in_out,
                    dateId,
                    categoryId,
                    accountId!!))
            return null
        }
    }

    private class InsertCategoryAsyncTask(val categoryDao: CategoryDao) : AsyncTask<CategoryEntitiy, Void, Void>() {
        override fun doInBackground(vararg category: CategoryEntitiy): Void? {
            categoryDao.insert(category[0])
            return null
        }
    }
}