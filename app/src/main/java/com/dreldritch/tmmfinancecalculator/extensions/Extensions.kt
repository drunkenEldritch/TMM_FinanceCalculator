package com.dreldritch.tmmfinancecalculator.extensions

import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity

//ArrayList
fun ArrayList<AccountEntity>.getAccountStrings(): ArrayList<String>{
    val list = ArrayList<String>()
    for(acc in this)
        list.add(acc.account)
    return list
}
