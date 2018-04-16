package com.dreldritch.tmmfinancecalculator.controller

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Menu
import android.view.MenuItem
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.dreldritch.tmmfinancecalculator.viewmodel.AddTransactionViewModel
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.dialogs.AccountDialogFragment
import com.dreldritch.tmmfinancecalculator.dialogs.CategoryDialogFragment
import com.dreldritch.tmmfinancecalculator.dialogs.DateDialogFragment
import com.dreldritch.tmmfinancecalculator.model.EntryDbRepository
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity
import com.dreldritch.tmmfinancecalculator.model.entities.FullTransactionData
import kotlinx.android.synthetic.main.activity_add_transaction.*

//TODO Set default Account on start
//TODO DateDialog size & buttons & landscape layout
//TODO Check landscape mode of all dialogs
//TODO Alternative to avoid crash on first start of app (NullPointerException because default data not initialized on first activity start)

class AddTransactionActivity : AppCompatActivity(), DateDialogFragment.OnAddDialogFragmentInteractionListener,
        AccountDialogFragment.OnAccountDialogInteractionListener, CategoryDialogFragment.OnCategoryInteractionListener {

    val priceFormat = "."

    private lateinit var transactionViewModel: AddTransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        /*Setup toolbar and menu*/
        setSupportActionBar(findViewById(R.id.entry_toolbar))
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        transactionViewModel = ViewModelProviders.of(this).get(AddTransactionViewModel::class.java)

        /*Account dialog setup*/
        transactionViewModel.getAllAccounts().observe(this, Observer<List<AccountEntity>> { accounts ->
            entry_txt_account.setOnClickListener { openDialog("AccountDialog", AccountDialogFragment.newInstance(accounts!!)) }
        })

        transactionViewModel.findDefaultAccount().observe(this, Observer { account ->
            if(account != null)
                transactionViewModel.setCurrentAccount(account)

        })

        transactionViewModel.getCurrentAccount().observe(this, Observer { accountEntity ->
            entry_txt_account.text = accountEntity?.account
        })

        /*Category dialog setup*/
        transactionViewModel.getAllCategories()
                .observe(this, Observer<List<CategoryEntity>> { categories ->
                    entry_txt_category.apply {
                        setOnClickListener { openDialog("CategoryDialog", CategoryDialogFragment.newInstance(categories!!)) }
                    }
                })

        transactionViewModel.getCurrentCategory().observe(this, Observer { categoryEntity ->
            if (categoryEntity != null) {
                entry_txt_category.text = categoryEntity.category

                entry_icon_text_view.apply {
                    text = categoryEntity.category.substring(0..1)
                    val shape = resources.getDrawable(R.drawable.category_icon_drawable, null) as GradientDrawable
                    shape.setColor(categoryEntity.iconColor)
                    background = shape
                }
            }
        })

        /*Date setup*/
        transactionViewModel.getCurrentDate().observe(this, Observer { dateEntity ->
            entry_txt_date.text = dateEntity?.date
        })

        entry_txt_date.setOnClickListener { openDialog("DateDialog", DateDialogFragment.newInstance()) }

        //Setup price
        entry_edit_price.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val price = s.toString()
                val decimalPos: Int
                if (!price.contains(priceFormat)) return else decimalPos = price.indexOf(priceFormat)

                if (price.substring(decimalPos).length > 3) {
                    val newPrice = price.substring(0..decimalPos + 2)
                    entry_edit_price.removeTextChangedListener(this)
                    entry_edit_price.setText(newPrice)
                    entry_edit_price.setSelection(newPrice.length)
                    entry_edit_price.addTextChangedListener(this)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    /*Appbar menu*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.add_transaction_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save_template -> {
            Toast.makeText(this, "BlaBlaSave", Toast.LENGTH_SHORT).show()
            true
        }

        //TODO Access with ViewModel
        R.id.action_save_entry -> {
            val fullDataObject = createFullDataObject()
            if (fullDataObject != null) {
                val repo = EntryDbRepository(application)
                repo.insertFullDataObject(fullDataObject)
                Toast.makeText(this, "Entry saved!", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            true
        }

    //Switch to last activity
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /*DialogInteractionListeners*/
    override fun onDateDialogInteraction(date: String) {
        transactionViewModel.setCurrentDate(null, date)
    }

    override fun onAccDialogInteraction(accountEntity: AccountEntity) {
        transactionViewModel.setCurrentAccount(accountEntity)
    }

    override fun onCategoryDialogInteraction(categoryEntity: CategoryEntity) {
        transactionViewModel.setCurrentCategory(categoryEntity)
    }

    /*Helper functions*/
    private fun openDialog(tag: String, dialog: DialogFragment) {
        val fm = supportFragmentManager
        dialog.show(fm, tag)
    }

    //TODO Check nullability of objects
    private fun createFullDataObject(): FullTransactionData? {

        if (entry_edit_name.text.isEmpty()) {
            Toast.makeText(this, R.string.no_name_error, Toast.LENGTH_SHORT).show()
            return null
        }

        if (entry_edit_price.text.isEmpty()) {
            Toast.makeText(this, R.string.no_price_error, Toast.LENGTH_SHORT).show()
            return null
        }

        return FullTransactionData(null,
                entry_edit_name.text.toString(),
                entry_edit_price.text.toString().toDouble(),
                entry_edit_description.text.toString(),
                if (entry_in_btn.isChecked) 1 else 0,
                transactionViewModel.getCurrentDate().value?.id,
                transactionViewModel.getCurrentDate().value?.date!!,
                transactionViewModel.getCurrentAccount().value?.id!!,
                transactionViewModel.getCurrentAccount().value?.account!!,
                transactionViewModel.getCurrentCategory().value?.id,
                transactionViewModel.getCurrentCategory().value?.category,
                transactionViewModel.getCurrentCategory().value?.iconColor)
    }
}