package com.dreldritch.tmmfinancecalculator.dialogs

import android.app.Application
import android.arch.persistence.room.Transaction
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.controller.AddTransactionActivity
import com.dreldritch.tmmfinancecalculator.model.TransactionDbRepository
import com.dreldritch.tmmfinancecalculator.model.entities.FullTransactionData
import kotlinx.android.synthetic.main.fragment_transaction_dialog.*

class TransactionOverviewDialog: DialogFragment() {

    private lateinit var transaction : FullTransactionData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null)
            transaction = arguments!!.getParcelable(TRANSACTION)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_transaction_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val color = transaction.icon_color ?: resources.getColor(R.color.icon_default ,null)
        transaction_dialog_toolbar.setBackgroundColor(color)

        name_value.text = transaction.name
        price_value.text = transaction.price.toString()
        date_value.text = transaction.date
        description_value.text = transaction.description
        category_value.text = transaction.category
        account_value.text = transaction.account
        type_value.text = if(transaction.in_out == 1) "in" else "out"

        back_button.setOnClickListener { dismiss() }

        delete_button.setOnClickListener {
            val repo = TransactionDbRepository(context?.applicationContext as Application)
            repo.removeTransaction(transaction)
            dismiss()
        }

        edit_button.setOnClickListener {
            val intent = Intent(context, AddTransactionActivity::class.java)
            intent.putExtra("transaction", transaction)
            startActivity(intent)
        }
    }



    companion object {
        const val TRANSACTION = "transaction"

        fun newInstance(transaction: FullTransactionData): TransactionOverviewDialog {
            val dialog = TransactionOverviewDialog()
            val bundle = Bundle()
            bundle.putParcelable(TRANSACTION, transaction)
            dialog.arguments = bundle
            return dialog
        }
    }
}