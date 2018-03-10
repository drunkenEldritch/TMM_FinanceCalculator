package com.dreldritch.tmmfinancecalculator

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add_entry.*
import android.R.array
import android.widget.ArrayAdapter
import android.widget.Spinner



class AddEntryActivity: AppCompatActivity(), AddEntryDialogFragment.OnAddDialogFragmentInteractionListener {

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
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("Essen", "Miete", "Essen", "Miete", "Essen", "Miete", "Essen", "Miete"))
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

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

    fun openDialog(header: String, type: String){
        val fm = supportFragmentManager
        val dialog = AddEntryDialogFragment.newInstance(header, type)
        dialog.show(fm, "DialogFragment")
    }

    fun openDateDialog(){
        openDialog("", AddEntryDialogFragment.DATEDIALOG)
    }
}