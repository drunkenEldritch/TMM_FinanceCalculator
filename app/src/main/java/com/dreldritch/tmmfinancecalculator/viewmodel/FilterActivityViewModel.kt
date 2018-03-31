package com.dreldritch.tmmfinancecalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.dreldritch.tmmfinancecalculator.model.EntryDbRepository

class FilterActivityViewModel(application: Application): AndroidViewModel(application){

    private val repository = EntryDbRepository(application)

    fun getAllDates() = repository.getAllDates()
    fun getAllTransactions() = repository.getAllTransactions()
    fun getAllTransactionsFromDate(date: String) = repository.getAllTransactionsFromDate(date)
}