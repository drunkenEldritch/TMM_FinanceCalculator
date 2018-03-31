package com.dreldritch.tmmfinancecalculator.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FullTransactionData(
        val id: Long?, val name: String,
        val price: Double,
        val description: String,
        val in_out: Int,
        val date_id: Long, val date: String,
        val account_id: Long, val account: String,
        val category_id: Long?, val category: String?, val icon_color: Int?)
    : Parcelable