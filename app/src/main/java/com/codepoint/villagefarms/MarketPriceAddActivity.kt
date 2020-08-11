package com.codepoint.villagefarms

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.EditText
import com.codepoint.villagefarms.models.Market
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_market_price_add.*
import java.text.SimpleDateFormat
import java.util.*

class MarketPriceAddActivity : AppCompatActivity() {

    // Declare objectId as global variable
    private var objectId:String? = null
    private var district:String? = null

    // Declare Views
    lateinit var txtMarketName: EditText
    lateinit var txtMarketPrice: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_price_add)

        // Get data from intent
        val intent = intent
        objectId = intent.getStringExtra("objectId")
        district = intent.getStringExtra("district")

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

    // Method to capitalize every first letter in word: extend String class
    private fun String.toTitleCase(): String = split(" ").map { it.capitalize() }.joinToString(" ")

    private fun saveMarketPrice(){
        val marketName = txtMarketName.text.toString().toTitleCase().trim()

        // Capture datetime when expense was created and store in created
        val sdf = SimpleDateFormat("yyyy-MM-dd, hh:mm:ss")
        val created = sdf.format(Date())

        // Implement Number format exception in try catch blocks to avoid app crashing
        val marketPrice: Double? = try {
            java.lang.Double.parseDouble(txtMarketPrice.text.toString())
        } catch (e: NumberFormatException) {
            null
        }

        // Validate advance form before saving to Firebase database
        if (marketName.isEmpty()){
            txtMarketName.error = "Market name is required"
            return
        } else if (marketPrice == null){
            txtMarketPrice.error = "Market price is required"
            return
        } else {
            val market = Market(
                marketName,
                marketPrice,
                created,
                ""
            )

            val ref = FirebaseDatabase.getInstance().getReference("settings/markets/$objectId")

            // Push the data to Firebase
            ref.push().setValue(market)

            // Clear registration form after saving advance
            txtMarketName.setText("")
            txtMarketPrice.setText("")


            // Display response message after saving farmer
            Snackbar.make(linear_layout, "Market Price successfully added to $district", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()

        }

    }
    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}