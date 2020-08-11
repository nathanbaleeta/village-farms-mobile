package com.codepoint.villagefarms

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_farmer_add_advance.*
import kotlinx.android.synthetic.main.activity_market_price_add.*

class MarketPriceAddActivity : AppCompatActivity() {

    // Declare Views
    lateinit var txtMarketName: EditText
    lateinit var txtMarketPrice: EditText

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

        // Initialize Views
        txtMarketName = findViewById(R.id.txtMarketName)
        txtMarketPrice = findViewById(R.id.txtMarketPrice)

        // Attach a click listener to save button
        btnSaveMarketPrice.setOnClickListener {
            saveMarketPrice()
        }

    }


    private fun saveMarketPrice(){
        // Validate advance form before saving to Firebase database
        if (txtMarketName == null){
            txtMarketName.error = "Market name is required"
            return
        } else if (txtMarketPrice == null){
            txtMarketPrice.error = "Market price is required"
            return
        } else {

        }

    }
    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}