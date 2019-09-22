package com.codepoint.villagefarms

import android.app.PendingIntent.getActivity
import android.content.ContentValues
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_sales_detail.*

class SalesDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_detail)

        // Get data from intent
        val intent = intent
        val objectId = intent.getStringExtra("objectId")
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")

        //actionbar
        val actionbar = supportActionBar

        //to change title of activity programmatically to fullname
       this.title = firstName.plus(" ").plus(lastName)

        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)




        // Text view for full name
        val fullName = findViewById<TextView>(R.id.info_text_fullName)

        //setText by concatenating firstName and lastName
        fullName.text = firstName.plus(" ").plus(lastName)


    }

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
