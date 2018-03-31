package com.dreldritch.tmmfinancecalculator.controller

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dreldritch.tmmfinancecalculator.viewmodel.AddEntryViewModel
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.extensions.getDateStrings
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.viewmodel.FilterActivityViewModel
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.app_bar_filter.*

class FilterActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        FilterListFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var filterActivityViewModel: FilterActivityViewModel
    private var dateList: List<DateEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        filterActivityViewModel = ViewModelProviders.of(this).get(FilterActivityViewModel::class.java)
        filterActivityViewModel.getAllDates().observe(this, Observer<List<DateEntity>> { dates ->
            val dateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dates!!.getDateStrings())
            dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            filter_date_spinner.apply {
                adapter = dateAdapter
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Toast.makeText(context, "Nothing!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        Toast.makeText(context, dates.getDateStrings()[position], Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        filterActivityViewModel.getAllTransactions().observe(this, Observer { transactions ->
            if(transactions != null){
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.filter_list_fragment_container,
                        FilterListFragment.newInstance(transactions))
                fragmentTransaction.commit()
            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
