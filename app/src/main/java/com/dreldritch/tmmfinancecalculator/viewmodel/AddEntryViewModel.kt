package com.dreldritch.tmmfinancecalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.dreldritch.tmmfinancecalculator.model.EntryDbRepository
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import java.text.SimpleDateFormat
import java.util.*

class AddEntryViewModel(application: Application): AndroidViewModel(application) {

    private val preferedFormat = "yyyy-MM-dd"

    private var dateEntity: DateEntity
    private var accountEntity: AccountEntity? = null
    private var categoryEntity: CategoryEntity? = null

    private val repository = EntryDbRepository(application)
    private var categories: LiveData<List<CategoryEntity>>?
    private var accounts: LiveData<List<AccountEntity>>?
    private var dates: LiveData<List<DateEntity>>?

    init {
        dateEntity = DateEntity(null,SimpleDateFormat(preferedFormat).format(Date()))
        categories = repository.getAllCategories()
        accounts = repository.getAllAccounts()
        dates = repository.getAllDates()
    }

    fun getCurrentDate(): DateEntity? = dateEntity
    fun setCurrentDate(id: Long?, date: String) {dateEntity = DateEntity(id, date) }

    fun getCurrentAccount(): AccountEntity? = accountEntity
    fun setCurrentAccount(accountEntity: AccountEntity) {this.accountEntity = accountEntity}

    fun getCurrentCategory(): CategoryEntity? = categoryEntity
    fun setCurrentCategory(categoryEntity: CategoryEntity) {
        this.categoryEntity = categoryEntity
    }

    /*DB queries*/
    fun getAllAccounts() = repository.getAllAccounts()
    fun getAllCategories() = repository.getAllCategories()
    fun getAllDates() = repository.getAllDates()
    fun getAllTransactions() = repository.getAllTransactions()
}
