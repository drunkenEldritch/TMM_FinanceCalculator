package com.dreldritch.tmmfinancecalculator.extensions

import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity

//ArrayList
fun List<AccountEntity>.getAccountStrings(): List<String>{
    val list = ArrayList<String>()
    for(acc in this)
        list.add(acc.account)
    return list
}

