package com.dreldritch.tmmfinancecalculator.model

import android.app.Application
import android.os.AsyncTask
import com.dreldritch.tmmfinancecalculator.model.dao.AccountDao
import com.dreldritch.tmmfinancecalculator.model.dao.CategoryDao
import com.dreldritch.tmmfinancecalculator.model.dao.DateDao
import com.dreldritch.tmmfinancecalculator.model.dao.EntryDao
import com.dreldritch.tmmfinancecalculator.model.database.EntryDatabase
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

    fun insertEntryObject(entry: EntryDataObject) {
        InsertAsyncTask(entryDao, dateDao).execute(entry)
    }

    fun getAllAccounts() = accountDao.getAll()

    fun getAllCategory() = categoryDao.getAll()

    fun isDbCreated() = EntryDatabase.getDatabase(application).getDatabaseCreated()

    private class InsertAsyncTask(val entryDao: EntryDao, val dateDao: DateDao) : AsyncTask<EntryDataObject, Void, Void>() {
        override fun doInBackground(vararg entry: EntryDataObject): Void? {

            var dateId = dateDao.insert(DateEntity(null, entry[0].date))
            if(dateId < 0) dateId = dateDao.getDateId(entry[0].date)

            entryDao.insert(EntryEntity(null,
                    entry[0].name,
                    entry[0].price,
                    entry[0].description,
                    entry[0].in_out,
                    dateId,
                    1,
                    1))
            return null
        }
    }
}