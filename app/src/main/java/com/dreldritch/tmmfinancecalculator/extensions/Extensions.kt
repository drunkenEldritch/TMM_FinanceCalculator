package com.dreldritch.tmmfinancecalculator.extensions

import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity

//ArrayList
fun List<AccountEntity>.getAccountStrings(): List<String>{
    val list = ArrayList<String>()
    for(acc in this)
        list.add(acc.account)
    return list
}

fun List<DateEntity>.getDateStrings() = this.map { it.date.substring(0..it.date.length - 4) }.toSet().toList()
