package com.dreldritch.tmmfinancecalculator

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.dreldritch.tmmfinancecalculator.model.EntryDbRepository
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntitiy
import java.text.SimpleDateFormat
import java.util.*

class AddEntryViewModel(application: Application): AndroidViewModel(application) {

    private val preferedFormat = "yyyy-MM-dd"
    private var currentDate: String
    private var currentAccount: String? = null
    private var currentCategory: String? = null
    private var categoryIconColor: Int? = null

    private val repository = EntryDbRepository(application)
    private var categories: LiveData<List<CategoryEntitiy>>?
    private var accounts: LiveData<List<AccountEntity>>?

    init {
        currentDate = SimpleDateFormat(preferedFormat).format(Date())
        categories = repository.getAllCategories()
        accounts = repository.getAllAccounts()
    }

    fun getCurrentDate(): String? = currentDate
    fun setCurrentDate(date: String) {currentDate = date}

    fun getCurrentAccount(): String? = currentAccount
    fun setCurrentAccount(account: String) {currentAccount = account}

    fun getCurrentCategory(): String? = currentCategory
    fun setCurrentCategory(category: String) {currentCategory = category}

    fun getIconColor(): Int? = categoryIconColor
    fun setIconColor(iconColor: Int) {categoryIconColor = iconColor}

    /*DB queries*/
    fun getAllAccounts() = repository.getAllAccounts()
    fun getAllCategories() = repository.getAllCategories()
}
