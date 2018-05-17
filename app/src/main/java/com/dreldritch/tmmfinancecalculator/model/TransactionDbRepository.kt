package com.dreldritch.tmmfinancecalculator.model

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import com.dreldritch.tmmfinancecalculator.model.dao.*
import com.dreldritch.tmmfinancecalculator.model.database.TransactionDatabase
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.TransactionEntity
import com.dreldritch.tmmfinancecalculator.model.entities.FullTransactionData

class TransactionDbRepository(application: Application){

    private val db = TransactionDatabase.getDatabase(application)
    private val dateDao: DateDao
    private val categoryDao: CategoryDao
    private val accountDao: AccountDao
    private val transactionDao: TransactionDao
    private val fullDataDao: FullTransactionDao

    init {
        dateDao = db.getDateDao()
        categoryDao = db.getCategoryDao()
        accountDao = db.getAccountDao()
        transactionDao = db.getTransactionDao()
        fullDataDao = db.getFullTransactionDataDao()
    }

    fun upsertFullDataObject(transaction: FullTransactionData){
        UpsertFullDataAsyncTask(db ,transactionDao, dateDao).execute(transaction)
    }

    fun insertCategory(category: CategoryEntity) {
        InsertCategoryAsyncTask(categoryDao).execute(category)
    }

    fun getAllAccounts() = accountDao.getAll()

    fun getAllCategories() = categoryDao.getAll()

    fun getAllDates() = dateDao.getAll()

    fun getAllTransactions() = fullDataDao.getAllTransactions()

    fun getAllTransactionsFromDate(date: String) = fullDataDao.getAllTransactionsFromDate(date)

    fun findAccountById(id: Long) = accountDao.findById(id)

    fun getCategoryEntity(category: String) = categoryDao.getCategoryEntity(category)

    fun removeCategory(category: CategoryEntity) {
        RemoveCategoryAsyncTask(categoryDao).execute(category)
    }

    fun removeTransaction(transaction: FullTransactionData) {
        RemoveTransactionAsyncTask(db, transactionDao, dateDao).execute(transaction)
    }

    private class UpsertFullDataAsyncTask(val db: TransactionDatabase, val transactionDao: TransactionDao, val dateDao: DateDao) : AsyncTask<FullTransactionData, Void, Void>() {
        override fun doInBackground(vararg transaction: FullTransactionData): Void? {

            var dateId = dateDao.insert(DateEntity(null, transaction[0].date))
            if(dateId < 0) dateId = dateDao.getDateId(transaction[0].date)

            var id: Long? = transaction[0].id

            val transactionEntity = TransactionEntity(id, transaction[0].name,
                    transaction[0].price, transaction[0].description, transaction[0].in_out,
                    dateId, transaction[0].category_id, transaction[0].account_id)

            if(id == null)
                id = transactionDao.insert(transactionEntity)
            else{
                db.runInTransaction {
                    transactionDao.update(transactionEntity)
                    if (transactionDao.checkForLastDate(transactionEntity.dateId) == 0) {
                        dateDao.deleteById(transactionEntity.dateId)
                        Log.i("Repository",
                                "id:${transactionEntity.dateId}")
                    }
                }
            }


            Log.d("TransactionDbRepository", "id = $id,\n" +
                    "name = ${transaction[0].name},\n" +
                    "price = ${transaction[0].price},\n" +
                    "desc = ${transaction[0].description},\n" +
                    "type = ${transaction[0].in_out},\n" +
                    "dateId = $dateId,\n" +
                    "catId = ${transaction[0].category_id},\n" +
                    "accId = ${transaction[0].account_id}\n" +
                    "with $transactionDao")
            return null
        }
    }

    private class InsertCategoryAsyncTask(val categoryDao: CategoryDao) : AsyncTask<CategoryEntity, Void, Void>() {
        override fun doInBackground(vararg category: CategoryEntity): Void? {
            categoryDao.insert(category[0])
            Log.d("TransactionDbRepository", "categoryID = ${category[0].id},\n" +
                    "category = ${category[0].category},\n" +
                    "iconColor = ${category[0].iconColor}\n" +
                    "with $categoryDao")
            return null
        }
    }

    private class RemoveCategoryAsyncTask(val categoryDao: CategoryDao) : AsyncTask<CategoryEntity, Void, Void>() {
        override fun doInBackground(vararg category: CategoryEntity): Void? {
            categoryDao.delete(category[0])
            Log.d("TransactionDbRepository", "categoryID = ${category[0].id},\n" +
                    "category = ${category[0].category},\n" +
                    "iconColor = ${category[0].iconColor}\n" +
                    "removed with $categoryDao")
            return null
        }
    }

    private class RemoveTransactionAsyncTask(val db: TransactionDatabase, val transactionDao: TransactionDao,
                                             val dateDao: DateDao) : AsyncTask<FullTransactionData, Void, Void>() {
        override fun doInBackground(vararg fullTransactionData: FullTransactionData): Void? {
            db.runInTransaction {
                transactionDao.deleteById(fullTransactionData[0].id!!)
                if (transactionDao.checkForLastDate(fullTransactionData[0].date_id!!) == 0) {
                    dateDao.deleteById(fullTransactionData[0].date_id!!)
                    Log.i("Repository",
                            "id:${fullTransactionData[0].date_id} " +
                                    "date: ${fullTransactionData[0].date} deleted!")
                }
            }
            return null
        }
    }
}