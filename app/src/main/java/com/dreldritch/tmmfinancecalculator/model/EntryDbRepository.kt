package com.dreldritch.tmmfinancecalculator.model

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import com.dreldritch.tmmfinancecalculator.model.dao.*
import com.dreldritch.tmmfinancecalculator.model.database.EntryDatabase
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.EntryEntity
import com.dreldritch.tmmfinancecalculator.model.entities.FullTransactionData

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

    fun insertFullDataObject(transaction: FullTransactionData){
        InsertFullDataAsyncTask(entryDao, dateDao).execute(transaction)
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

    private class InsertFullDataAsyncTask(val entryDao: EntryDao, val dateDao: DateDao) : AsyncTask<FullTransactionData, Void, Void>() {
        override fun doInBackground(vararg transaction: FullTransactionData): Void? {

            var dateId = dateDao.insert(DateEntity(null, transaction[0].date))
            if(dateId < 0) dateId = dateDao.getDateId(transaction[0].date)

            val id = entryDao.insert(EntryEntity(null,
                    transaction[0].name,
                    transaction[0].price,
                    transaction[0].description,
                    transaction[0].in_out,
                    dateId,
                    transaction[0].category_id,
                    transaction[0].account_id ?: 1))

            Log.d(REPOSITORY_TAG, "id = $id,\n" +
                    "name = ${transaction[0].name},\n" +
                    "price = ${transaction[0].price},\n" +
                    "desc = ${transaction[0].description},\n" +
                    "type = ${transaction[0].in_out},\n" +
                    "dateId = $dateId,\n" +
                    "catId = ${transaction[0].category_id},\n" +
                    "accId = ${transaction[0].account_id ?: 1}\n" +
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