package com.dreldritch.tmmfinancecalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.dreldritch.tmmfinancecalculator.model.EntryDbRepository
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity

class FilterActivityViewModel(application: Application): AndroidViewModel(application){

    private val repository = EntryDbRepository(application)

    private var categories: LiveData<List<CategoryEntity>>
    //private var accounts: LiveData<List<AccountEntity>>
    private var dates: LiveData<List<DateEntity>>?

    init {
        dates = repository.getAllDates()
        categories = repository.getAllCategories()
        //accounts = repository.getAllAccounts()
    }

    fun getAllDates() = repository.getAllDates()
    fun getAllTransactions() = repository.getAllTransactions()
    fun getAllTransactionsFromDate(date: String) = repository.getAllTransactionsFromDate(date)
}