package com.dreldritch.tmmfinancecalculator.model

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import com.dreldritch.tmmfinancecalculator.model.dao.*
import com.dreldritch.tmmfinancecalculator.model.database.EntryDatabase
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.EntryDataObject
import com.dreldritch.tmmfinancecalculator.model.entities.EntryEntity

private const val REPOSITORY_TAG = "EntryDbRepository"

class EntryDbRepository(val application: Application){

    private val dateDao: DateDao
    private val categoryDao: CategoryDao
    private val accountDao: AccountDao
    private val entryDao: EntryDao
    private val fullDataDao: FullTransactionDao

    init {
        val db = EntryDatabase.getDatabase(application)
        dateDao = db.getDateDao()
        categoryDao = db.getCategoryDao()
        accountDao = db.getAccountDao()
        entryDao = db.getEntryDao()
        fullDataDao = db.getFullTransactionDataDao()
    }

    fun insertEntryObject(entry: EntryDataObject){
        InsertEntryDataAsyncTask(entryDao, dateDao).execute(entry)
    }

    fun insertCategory(category: CategoryEntity) {
        InsertCategoryAsyncTask(categoryDao).execute(category)
    }

    fun getAllAccounts() = accountDao.getAll()

    fun getAllCategories() = categoryDao.getAll()

    fun getAllDates() = dateDao.getAll()

    fun getAllTransactions() = fullDataDao.getAllTransactions()

    fun getAllTransactionsFromDate(date: String) = fullDataDao.getAllTransactionsFromDate(date)

    /*fun isDbCreated() = EntryDatabase.getDatabase(application).getDatabaseCreated()*/

    private class InsertEntryDataAsyncTask(val entryDao: EntryDao, val dateDao: DateDao) : AsyncTask<EntryDataObject, Void, Void>() {
        override fun doInBackground(vararg entry: EntryDataObject): Void? {

            var dateId = dateDao.insert(DateEntity(null, entry[0].date))
            if(dateId < 0) dateId = dateDao.getDateId(entry[0].date)

            val categoryId = if(entry[0].categoryEntity == null) null else entry[0].categoryEntity!!.id

            //1 = default account
            val accountId = if(entry[0].accountEntity == null) 1 else entry[0].accountEntity!!.id

            val id = entryDao.insert(EntryEntity(null,
                    entry[0].name,
                    entry[0].price,
                    entry[0].description,
                    entry[0].in_out,
                    dateId,
                    categoryId,
                    accountId!!))

            Log.d(REPOSITORY_TAG, "id = $id,\n" +
                    "name = ${entry[0].name},\n" +
                    "price = ${entry[0].price},\n" +
                    "desc = ${entry[0].description},\n" +
                    "type = ${entry[0].in_out},\n" +
                    "dateId = $dateId,\n" +
                    "catId = $categoryId,\n" +
                    "accId = $accountId\n" +
                    "with $entryDao")
            return null
        }
    }

    private class InsertCategoryAsyncTask(val categoryDao: CategoryDao) : AsyncTask<CategoryEntity, Void, Void>() {
        override fun doInBackground(vararg category: CategoryEntity): Void? {
            categoryDao.insert(category[0])
            Log.d(REPOSITORY_TAG, "categoryID = ${category[0].id},\n" +
                    "category = ${category[0].category},\n" +
                    "iconColor = ${category[0].iconColor}\n" +
                    "with $categoryDao")
            return null
        }
    }
}