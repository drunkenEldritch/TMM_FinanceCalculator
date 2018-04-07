package com.dreldritch.tmmfinancecalculator.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dreldritch.tmmfinancecalculator.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_main_activity.setOnClickListener {
            val addIntent = Intent(this, AddTransactionActivity::class.java)
            startActivity(addIntent)
        }

        BBbutton.setOnClickListener{
            startActivity(Intent(this, FilterActivity::class.java))
        }
    }
}
