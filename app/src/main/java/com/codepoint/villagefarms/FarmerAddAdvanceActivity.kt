package com.codepoint.villagefarms

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class FarmerAddAdvanceActivity : AppCompatActivity() {

    // Declare as global variables
    private var objectId:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_add_advance)

        // Get data from intent
        val intent = intent
        objectId = intent.getStringExtra("objectId")

        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = ("Create Advance")

        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)

    }

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
