package com.dreldritch.tmmfinancecalculator.controller

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.R.id.nav_view
import com.dreldritch.tmmfinancecalculator.adapter.ExpandableListDateAdapter
import com.dreldritch.tmmfinancecalculator.dialogs.TransactionOverviewDialog
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.FullTransactionData
import com.dreldritch.tmmfinancecalculator.viewmodel.FilterActivityViewModel
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.app_bar_filter.*
import kotlinx.android.synthetic.main.fragment_filter_list.*
import java.util.*

class FilterActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        FilterListFragment.OnFragmentInteractionListener {

    private lateinit var filterActivityViewModel: FilterActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val addIntent = Intent(this, AddTransactionActivity::class.java)
            startActivity(addIntent)
        }

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        filterActivityViewModel = ViewModelProviders.of(this).get(FilterActivityViewModel::class.java)

        filterActivityViewModel.getAllDates().observe(this, Observer<List<DateEntity>> { dates ->
            if(dates != null && dates.isNotEmpty()){
                //Cache dates
                val monthList = dates.map { it.date.substring(0..it.date.length - 4) }.distinct().sortedDescending()

                //Initialize adapter for spinner
                val dateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monthList)
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                //Initialize spinner
                filter_date_spinner.apply {
                    adapter = dateAdapter
                    onItemSelectedListener = MonthSpinnerAdapterListener(context, monthList)
                }

                //Initialize ExpandableList
                setCurrentTransactions(monthList.first())
            }else{
                //Initialize adapter for spinner
                val dateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("-empty-"))
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                filter_date_spinner.adapter = dateAdapter
            }
        })

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    private fun setCurrentTransactions(date:String){
        val fragment = supportFragmentManager.findFragmentById(R.id.filter_fragment)

        fragment.exp_list_view.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            val child = parent.expandableListAdapter.getChild(groupPosition, childPosition) as FullTransactionData
            TransactionOverviewDialog.newInstance(child).show(supportFragmentManager, "TransactionOverviewDialog")
            true
        }

        filterActivityViewModel.getAllTransactionsFromDate("%$date%")
                .observe(this, Observer { transactions ->
                    if(transactions != null){
                        fragment.exp_list_view.setAdapter(ExpandableListDateAdapter(this, transactions))
                        filter_total_sum.text = String.format(Locale.ROOT, "%.2f", transactions.sumByDouble { it.price })
                    }
                })
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        menuInflater.inflate(R.menu.filter_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_overview -> {
                // Handle the camera action
            }
            R.id.nav_filter -> {

            }
            R.id.nav_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    inner class MonthSpinnerAdapterListener(val context: Context, private val monthList: List<String>): AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            setCurrentTransactions(monthList[position])
            //Toast.makeText(context, monthList[position], Toast.LENGTH_SHORT).show()
        }
    }
}
