package com.dreldritch.tmmfinancecalculator.model.entities

data class EntryDataObject(
        val id: Long?,
        var name: String,
        var price: Double,
        var description: String,
        var in_out: Int,
        var date: String,
        var categoryEntity: CategoryEntity?,
        var accountEntity: AccountEntity?)