package com.dreldritch.tmmfinancecalculator.model.entities

data class EntryDataObject(
        val id: Long?,
        var name: String,
        var price: Double,
        var description: String,
        var in_out: Int,
        var date: String,
        var categoryEntitiy: CategoryEntitiy?,
        var accountEntity: AccountEntity?
)