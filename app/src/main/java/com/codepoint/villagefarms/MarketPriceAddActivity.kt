package com.codepoint.villagefarms

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MarketPriceAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_price_add)

        // Get data from intent
        val intent = intent
        val objectId = intent.getStringExtra("objectId")
        val district = intent.getStringExtra("district")

        //to change title of activity programmatically to full name
        val actionBar = supportActionBar

        actionBar!!.title = ("Add Price to ").plus(district)

        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)

    }
    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}