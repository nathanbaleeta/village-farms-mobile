package com.codepoint.villagefarms

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MarketListByDistrictActivity : AppCompatActivity() {


    // Declare as global variables
    private var objectId:String? = null
    private var district:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_list_by_district)

        // Get data from intent
        val intent = intent
        objectId = intent.getStringExtra("objectId")
        district = intent.getStringExtra("district")

        //actionbar
        val actionbar = supportActionBar

        //to change title of activity programmatically to full name
        this.title = district.plus(" ").plus("Market list")

        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)


    }
    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}