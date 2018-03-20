package com.dreldritch.tmmfinancecalculator.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

class EntryDbViewModel(application: Application): AndroidViewModel(application) {
    val repository = EntryDbRepository(application)

    fun getAllAccounts() = repository.getAllAccounts()
    fun getAllCategories() = repository.getAllCategory()
}
