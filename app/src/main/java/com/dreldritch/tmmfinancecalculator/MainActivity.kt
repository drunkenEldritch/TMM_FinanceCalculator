package com.dreldritch.tmmfinancecalculator

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_main_activity.setOnClickListener {
            val addIntent = Intent(this, AddEntryActivity::class.java)
            startActivity(addIntent)
        }
    }
}
