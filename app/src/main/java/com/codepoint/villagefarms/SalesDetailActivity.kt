package com.codepoint.villagefarms

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_sales_detail.*

class SalesDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_detail)

        //actionbar
        val actionbar = supportActionBar

        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)


        // Get data from intent
        val intent = intent
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")

        // Text view for full name
        val fullName = findViewById<TextView>(R.id.info_text_fullName)

        //setText
        fullName.text = firstName + " " + lastName



    }

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
