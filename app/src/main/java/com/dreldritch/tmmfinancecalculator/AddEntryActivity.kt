package com.dreldritch.tmmfinancecalculator

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add_entry.*
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dreldritch.tmmfinancecalculator.model.EntryDbRepository
import com.dreldritch.tmmfinancecalculator.model.entities.EntryDataObject
import java.text.SimpleDateFormat
import java.util.*


class AddEntryActivity: AppCompatActivity(), DateDialogFragment.OnAddDialogFragmentInteractionListener {

    //TODO
    val priceFormat = "."
    val DATESORT = "yyyy-MM-dd"
    val DATESLASH = "dd/MM/yyyy"
    val DATEPOINT = "dd.MM.yyyy"
    val preferedFormat = DATESORT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        /*Setup toolbar and menu*/
        setSupportActionBar(findViewById(R.id.entry_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        entry_txt_date.setText(SimpleDateFormat(preferedFormat).format(Date()))
        entry_txt_date.setOnClickListener {
            openDialog("DateDialog", DateDialogFragment.newInstance())
        }

        //Account Spinner
        val accSpinner = entry_acc_spinner
        val accAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("Konto1", "Konto2"))
        accAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        accSpinner.adapter = accAdapter

        //Category Spinner
        val categorySpinner = entry_category_spinner
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("Essen", "Miete", "Essen", "Miete", "Essen", "Miete", "Essen", "Miete", "Essen", "Miete", "Essen", "Miete", "Essen", "Miete", "Essen", "Miete"))
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        //Price editText configuration
        /*entry_edit_price.setSelectAllOnFocus(true)*/
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
            entryRepository.insert(createEntryDbObject())
            Toast.makeText(this, "Entry saved!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            true
        }

        else -> { super.onOptionsItemSelected(item) }
    }

    override fun onDateDialogInteraction(date: String) {
        entry_txt_date.setText(date)
    }

    private fun openDialog(tag: String, dialog: DialogFragment){
        val fm = supportFragmentManager
        dialog.show(fm, tag)
    }

    private fun createEntryDbObject()
            : EntryDataObject{

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