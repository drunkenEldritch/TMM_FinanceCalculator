package com.dreldritch.tmmfinancecalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.dreldritch.tmmfinancecalculator.model.EntryDbRepository
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionViewModel(application: Application): AndroidViewModel(application) {

    private val preferedFormat = "yyyy-MM-dd"

    private val repository = EntryDbRepository(application)

    private var dateEntity =  MutableLiveData<DateEntity>()
    private var accountEntity = MutableLiveData<AccountEntity>()
    private var categoryEntity = MutableLiveData<CategoryEntity?>()

    private var categories: LiveData<List<CategoryEntity>>
    private var accounts: LiveData<List<AccountEntity>>
    //private var dates: LiveData<List<DateEntity>>?

    init {
        //dates = repository.getAllDates()
        categories = repository.getAllCategories()
        accounts = repository.getAllAccounts()

        accountEntity.value = AccountEntity(1, "deaf")
        dateEntity.value = DateEntity(null,SimpleDateFormat(preferedFormat).format(Date()))
        categoryEntity.value = null
    }

    fun getCurrentDate(): MutableLiveData<DateEntity> = dateEntity
    fun setCurrentDate(id: Long?, date: String) {dateEntity.value = DateEntity(id, date) }

    fun getCurrentAccount(): LiveData<AccountEntity> = accountEntity
    fun setCurrentAccount(account: AccountEntity) {accountEntity.value = account}
    fun findAccountById(id: Long)= repository.findAccountById(id)
    fun findDefaultAccount()= repository.findAccountById(1)

    fun getCurrentCategory(): MutableLiveData<CategoryEntity?> = categoryEntity
    fun setCurrentCategory(category: CategoryEntity) { categoryEntity.value = category }

    /*DB queries*/
    fun getAllAccounts() = accounts
    fun getAllCategories() = categories
    //fun getAllDates() = dates
    //fun getAllTransactions() = repository.getAllTransactions()

    fun insertCategory(category: CategoryEntity) = repository.insertCategory(category)
}
