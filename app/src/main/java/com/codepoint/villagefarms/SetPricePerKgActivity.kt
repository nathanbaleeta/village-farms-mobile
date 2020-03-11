package com.codepoint.villagefarms

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.codepoint.villagefarms.models.District
import com.codepoint.villagefarms.models.Price
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_set_price.*
import kotlinx.android.synthetic.main.district_dialog.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SetPricePerKgActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_price)


        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = ("Set Price Per Kg")

        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)


        // Initialize linear layout for radioGroup
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        val radioGroup = RadioGroup(this)
        radioGroup.orientation = RadioGroup.VERTICAL

        // Get a reference to our farmer's advances
        val refDistricts = FirebaseDatabase.getInstance()
            .getReference("settings/districts")

        val districtList = ArrayList<String>()

        // Attach a listener to read the data at our settings/districts reference
        refDistricts.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // Eliminates duplicates inn arrayList when old list is updated from Fire base
                districtList.clear()

                for (ds in dataSnapshot.children) {
                    val districtName: String? = ds.child("district").getValue(String()::class.java)
                    districtList.add(districtName!!)

                }

                // remove all radio buttons from radio group on data change and load afresh
                radioGroup.removeAllViews()
                linearLayout.removeView(radioGroup)

                // Eliminate duplicate districts
                val districts = districtList.distinct()

                for(option in districts){
                    val radioButton = RadioButton(this@SetPricePerKgActivity)
                    radioButton.text = option
                    radioGroup.addView(radioButton)
                }

                // Add radio group to linear layout declared earlier
                linearLayout.addView(radioGroup)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }


        })

        // Event listener for district radio group & buttons

        var district = ""
        radioGroup.setOnCheckedChangeListener { _, _ ->

            var id: Int = radioGroup.checkedRadioButtonId

            // If any radio button checked from radio group
            if (id!=-1){
                // Get the instance of radio button using id
                val radio:RadioButton = findViewById(id)

                district = radio.text.toString()
                Log.d(ContentValues.TAG, "${radio.text}")

            }else{
                // If no radio button checked in this radio group

                Log.d(ContentValues.TAG, "Nothing selected")
            }

        }

        // Attach a click listener to save button
        fabSetPrice.setOnClickListener {
            // Save price per kf info to fire base
            // Capture datetime when expense was created and store in created
            val sdf = SimpleDateFormat("dd/MM/yyyy, hh:mm:ss")
            val created = sdf.format(Date())

            // Implement Number format exception in try catch blocks to avoid app crashing
            val pricePerKg: Double? = try {
                java.lang.Double.parseDouble(txtPricePriceKg.text.toString())
            } catch (e: NumberFormatException) {
                null
            }

            if(district.isNotEmpty() && pricePerKg !== null) {

                val priceObj = Price(
                    district,
                    created,
                    pricePerKg,""

                )

                val ref = FirebaseDatabase.getInstance().getReference("settings/prices")

                // Push the data to Fire base cloud data store
                ref.push().setValue(priceObj)

                // Clear PricePerKg form post save action
                radioGroup.clearCheck()
                txtPricePriceKg?.setText("")


                // Display response message after saving district
                Toast.makeText(applicationContext,
                    "Successfully saved district", Toast.LENGTH_SHORT).show()


            } else if (pricePerKg == null){
                txtPricePriceKg.error = "Price per kg is required"
                // Display response message after saving district
                Toast.makeText(applicationContext,
                    "Failed to save district", Toast.LENGTH_SHORT).show()
            } else if (radioGroup.checkedRadioButtonId == -1) {
                // no radio buttons are checked
                Toast.makeText(applicationContext,
                    "Missing district field", Toast.LENGTH_LONG).show()

        }

        }

    }

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

