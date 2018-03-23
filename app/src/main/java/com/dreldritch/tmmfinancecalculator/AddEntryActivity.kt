package com.dreldritch.tmmfinancecalculator

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Menu
import android.view.MenuItem
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.dreldritch.tmmfinancecalculator.dialogs.AccountDialogFragment
import com.dreldritch.tmmfinancecalculator.dialogs.CategoryDialogFragment
import com.dreldritch.tmmfinancecalculator.dialogs.DateDialogFragment
import com.dreldritch.tmmfinancecalculator.model.EntryDbRepository
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntitiy
import com.dreldritch.tmmfinancecalculator.model.entities.EntryDataObject
import kotlinx.android.synthetic.main.activity_add_entry.*

//TODO Save state of activity?
//TODO Set default Account on start
//TODO DateDialog size & buttons & landscape layout
//TODO Check landscape mode of all dialogs
//TODO Alternative to avoid crash on first start of app (NullPointerException because default data not initialized on first activity start)

class AddEntryActivity: AppCompatActivity(), DateDialogFragment.OnAddDialogFragmentInteractionListener,
AccountDialogFragment.OnAccountDialogInteractionListener, CategoryDialogFragment.OnCategoryInteractionListener{

    val priceFormat = "."
    val dateFormat = "yyyy-MM-dd"
    val preferedFormat = dateFormat

    private lateinit var entryViewModel: AddEntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        /*Setup toolbar and menu*/
        setSupportActionBar(findViewById(R.id.entry_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        entryViewModel = ViewModelProviders.of(this).get(AddEntryViewModel::class.java)

        /*Account dialog setup*/
        entryViewModel.getAllAccounts()
                .observe(this, android.arch.lifecycle.Observer<List<AccountEntity>> {accounts ->
            entry_txt_account.apply {
                if(entryViewModel.getCurrentAccount() != null)  text = entryViewModel.getCurrentAccount()
                setOnClickListener { openDialog("AccountDialog", AccountDialogFragment.newInstance(accounts!!))}
            }
        })

        /*Category dialog setup*/
        entryViewModel.getAllCategories()
                .observe(this, android.arch.lifecycle.Observer<List<CategoryEntitiy>> {categories ->
            entry_txt_category.apply {
                setOnClickListener { openDialog("CategoryDialog", CategoryDialogFragment.newInstance(categories!!)) }
            }
        })

        if(entryViewModel.getCurrentCategory() != null) {
            entry_txt_category.text = entryViewModel.getCurrentCategory()

            entry_icon_text_view.apply {
                text = entryViewModel.getCurrentCategory()!!.substring(0..1)
                val shape = resources.getDrawable(R.drawable.category_icon_drawable, null) as GradientDrawable
                shape.setColor(entryViewModel.getIconColor()!!)
                background = shape
            }
        }

        /*Date setup*/
        entry_txt_date.apply {
            text = entryViewModel.getCurrentDate()
            setOnClickListener { openDialog("DateDialog", DateDialogFragment.newInstance()) }
        }

        //Setup price
        entry_edit_price.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val price = s.toString()
                val decimalPos : Int
                if(!price.contains(priceFormat)) return else decimalPos = price.indexOf(priceFormat)

                if (price.substring(decimalPos).length > 3){
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
        menuInflater.inflate(R.menu.add_entry_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save_template -> {
            // User chose the "Settings" item, show the app settings UI...
            true
        }

        R.id.action_save_entry -> {
            val entryRepository = EntryDbRepository(application)
            entryRepository.insertEntryObject(createEntryDbObject())
            Toast.makeText(this, "Entry saved!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            true
        }
        else -> { super.onOptionsItemSelected(item) }
    }

    /*DialogInteractionListeners*/
    override fun onDateDialogInteraction(date: String) {
        entryViewModel.setCurrentDate(date)
        entry_txt_date.text = entryViewModel.getCurrentDate()
    }

    override fun onAccDialogInteraction(account: String) {
        entryViewModel.setCurrentAccount(account)
        entry_txt_account.text = entryViewModel.getCurrentAccount()
    }

    override fun onCategoryDialogInteraction(category: String, iconColor: Int) {
        entryViewModel.setCurrentCategory(category)
        entry_txt_category.text = entryViewModel.getCurrentCategory()

        entryViewModel.setIconColor(iconColor)

        entry_icon_text_view.apply {
            text = entryViewModel.getCurrentCategory()!!.substring(0..1)
            val shape = resources.getDrawable(R.drawable.category_icon_drawable, null) as GradientDrawable
            shape.setColor(entryViewModel.getIconColor()!!)
            background = shape
        }
    }

    /*Helper functions*/
    private fun openDialog(tag: String, dialog: DialogFragment){
        val fm = supportFragmentManager
        dialog.show(fm, tag)
    }

    private fun createEntryDbObject(): EntryDataObject{
        val inOut = if(entry_in_btn.isChecked) 1 else 0
        return EntryDataObject(null,
                entry_edit_name.text.toString(),
                entry_edit_price.text.toString().toDouble(),
                entry_edit_description.text.toString(),
                inOut,
                null,
                entry_txt_date.text.toString(),
                null,
                "none",
                null,
                "konto1")
    }
}