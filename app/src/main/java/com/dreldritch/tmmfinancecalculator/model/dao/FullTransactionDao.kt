package com.dreldritch.tmmfinancecalculator.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dreldritch.tmmfinancecalculator.model.entities.*

@Dao
abstract class FullTransactionDao {
    @Query ("SELECT ${TransactionEntity.TABLE_NAME}.id, " +
            "${TransactionEntity.TABLE_NAME}.name, " +
            "${TransactionEntity.TABLE_NAME}.price, " +
            "${TransactionEntity.TABLE_NAME}.description, " +
            "${TransactionEntity.TABLE_NAME}.in_out, " +
            "${TransactionEntity.TABLE_NAME}.date_id, ${DateEntity.TABLE_NAME}.date, " +
            "${TransactionEntity.TABLE_NAME}.account_id, ${AccountEntity.TABLE_NAME}.account, " +
            "${TransactionEntity.TABLE_NAME}.category_id, ${CategoryEntity.TABLE_NAME}.category, ${CategoryEntity.TABLE_NAME}.icon_color " +
            "FROM ${TransactionEntity.TABLE_NAME} " +
            "INNER JOIN ${DateEntity.TABLE_NAME} ON " +
            "${TransactionEntity.TABLE_NAME}.date_id = ${DateEntity.TABLE_NAME}.id " +
            "INNER JOIN ${AccountEntity.TABLE_NAME} ON " +
            "${TransactionEntity.TABLE_NAME}.account_id = ${AccountEntity.TABLE_NAME}.id " +
            "LEFT JOIN ${CategoryEntity.TABLE_NAME}  ON " +
            "${TransactionEntity.TABLE_NAME}.category_id = ${CategoryEntity.TABLE_NAME}.id")
    abstract fun getAllTransactions(): LiveData<List<FullTransactionData>>

    @Query ("SELECT Entries.id, Entries.name, Entries.price, Entries.description, Entries.in_out, Entries.date_id, Dates.date, " +
            "Entries.account_id, Accounts.account, Entries.category_id, Categories.category, Categories.icon_color " +
            "FROM Entries " +
            "INNER JOIN Dates ON Entries.date_id = Dates.id " +
            "INNER JOIN Accounts ON Entries.account_id = Accounts.id " +
            "LEFT JOIN Categories ON Entries.category_id = Categories.id " +
            "WHERE Dates.date LIKE :date")
    abstract fun getAllTransactionsFromDate(date: String): LiveData<List<FullTransactionData>>
}

/*
@Query ("SELECT ${TransactionEntity.TABLE_NAME}.id, " +
        "${TransactionEntity.TABLE_NAME}.name, " +
        "${TransactionEntity.TABLE_NAME}.price, " +
        "${TransactionEntity.TABLE_NAME}.description, " +
        "${TransactionEntity.TABLE_NAME}.in_out, " +
        "${TransactionEntity.TABLE_NAME}.date_id, ${DateEntity.TABLE_NAME}.date, " +
        "${TransactionEntity.TABLE_NAME}.account_id, ${AccountEntity.TABLE_NAME}.account, " +
        "${TransactionEntity.TABLE_NAME}.category_id, ${CategoryEntity.TABLE_NAME}.category, ${CategoryEntity.TABLE_NAME}.icon_color " +
        "FROM ${TransactionEntity.TABLE_NAME} " +
        "INNER JOIN ${DateEntity.TABLE_NAME} ON " +
        "${TransactionEntity.TABLE_NAME}.date_id = ${DateEntity.TABLE_NAME}.id " +
        "INNER JOIN ${AccountEntity.TABLE_NAME} ON " +
        "${TransactionEntity.TABLE_NAME}.account_id = ${AccountEntity.TABLE_NAME}.id " +
        "LEFT JOIN ${CategoryEntity.TABLE_NAME}  ON " +
        "${TransactionEntity.TABLE_NAME}.category_id = ${CategoryEntity.TABLE_NAME}.id " +
        "WHERE ${DateEntity.TABLE_NAME}.date LIKE :date")*/
