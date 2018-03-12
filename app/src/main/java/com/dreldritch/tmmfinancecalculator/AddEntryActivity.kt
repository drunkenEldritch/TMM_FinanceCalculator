package com.dreldritch.tmmfinancecalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add_entry.*
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter


class AddEntryActivity: AppCompatActivity(), AddEntryDialogFragment.OnAddDialogFragmentInteractionListener {

    val priceFormat = "."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        /*Setup toolbar and menu*/
        setSupportActionBar(findViewById(R.id.entry_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        entry_edit_date.setOnClickListener {
            openDateDialog()
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
        entry_edit_price.setSelectAllOnFocus(true)
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
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onFragmentInteraction() {
    }

    private fun openDialog(header: String, type: String){
        val fm = supportFragmentManager
        val dialog = AddEntryDialogFragment.newInstance(header, type)
        dialog.show(fm, "DialogFragment")
    }

    private fun openDateDialog(){
        openDialog("", AddEntryDialogFragment.DATEDIALOG)
    }


}