package com.dreldritch.tmmfinancecalculator

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.dreldritch.tmmfinancecalculator.model.EntryDbRepository
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntitiy
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import java.text.SimpleDateFormat
import java.util.*

class AddEntryViewModel(application: Application): AndroidViewModel(application) {

    private val preferedFormat = "yyyy-MM-dd"

    private var dateEntity: DateEntity
    private var accountEntity: AccountEntity? = null
    private var categoryEntitiy: CategoryEntitiy? = null

    private val repository = EntryDbRepository(application)
    private var categories: LiveData<List<CategoryEntitiy>>?
    private var accounts: LiveData<List<AccountEntity>>?

    init {
        dateEntity = DateEntity(null,SimpleDateFormat(preferedFormat).format(Date()))
        categories = repository.getAllCategories()
        accounts = repository.getAllAccounts()
    }

    fun getCurrentDate(): DateEntity? = dateEntity
    fun setCurrentDate(id: Long?, date: String) {dateEntity = DateEntity(id, date) }

    fun getCurrentAccount(): AccountEntity? = accountEntity
    fun setCurrentAccount(accountEntity: AccountEntity) {this.accountEntity = accountEntity}

    fun getCurrentCategory(): CategoryEntitiy? = categoryEntitiy
    fun setCurrentCategory(categoryEntitiy: CategoryEntitiy) {
        this.categoryEntitiy = categoryEntitiy
    }

    /*DB queries*/
    fun getAllAccounts() = repository.getAllAccounts()
    fun getAllCategories() = repository.getAllCategories()
}
