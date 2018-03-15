package com.dreldritch.tmmfinancecalculator.model.entities

/**
 * Created by cerox on 14.03.2018.
 */
data class EntryDataObject(
        val id: Long?,
        var name: String,
        var price: Double,
        var description: String,
        var in_out: Int,
        val dateId: Long?,
        var date: String,
        val categoryId: Long?,
        var category: String,
        val accountId: Long?,
        var account: String
)