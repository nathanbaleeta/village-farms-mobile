package com.codepoint.villagefarms

import PriceAdapter
import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import com.codepoint.villagefarms.models.Price
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_price_list.*
import kotlinx.android.synthetic.main.district_dialog.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_farmer_add_advance.view.*

import java.lang.Double.parseDouble


class PricePerKgListActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_price_list)


        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = ("Price per kg")

        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        val recyclerView = price_list_recycler_view

         recyclerView.setHasFixedSize(true)

        // Use a linear layout manager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        // Divider in-built item decoration
        recyclerView!!.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )




        /************************** RecyclerView Adapter *************************/


        // Get a reference to our farmer's advances
        val ref = FirebaseDatabase.getInstance().getReference("settings/prices")


        // Attach a listener to read the data at our farmers reference
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val priceList = ArrayList<Price>()

                // Eliminates duplicate list rows when child added or removed in Firebase
                priceList.clear()

                for (ds in dataSnapshot.children) {
                    val price = ds.getValue(Price::class.java)

                    // Extract object ID key from Fire base and assign to arrayList
                    price?.objectId = ds.key

                    priceList.add(price!!)
                    Log.d(ContentValues.TAG, price.toString())

                }

                // specify an adapter
                var adapter = PriceAdapter(priceList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        /************************** End of RecyclerView Adapter *************************/

        /************************** Start PricePerKg dialog ****************************/


        // Add district by inflating district dialog builder
        fabAddPricePerKg.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.price_dialog, null)

            // Define district radio group and attach to price dialog
            val districtRadioGroup = mDialogView.findViewById<RadioGroup>(R.id.rg_district)

            // Populate radio button with array options of district
            val districtOptionsArray = resources.getStringArray(R.array.districtList)
            for (district in districtOptionsArray) {
                val rb = RadioButton(this)
                rb.text = district
                districtRadioGroup.addView(rb)
            }

            val builder = AlertDialog.Builder(this)
            builder.setView(mDialogView)
            builder.setTitle("Set Price per kg")
            builder.setPositiveButton(android.R.string.yes) { dialog, _ ->

                //get text from EditTexts of custom layout
                val district = "Kamuli"

                val pricePerKg = parseDouble(mDialogView.txtPricePriceKg.text.toString())

                // Capture datetime when expense was created and store in created
                val sdf = SimpleDateFormat("yyyy-MM-dd, hh:mm:ss")
                val dateConfigured = sdf.format(Date())

                if(district.isNotEmpty()) {

                    val districtObj = Price(
                        district,
                        dateConfigured,
                        pricePerKg,
                        ""
                    )

                    val ref = FirebaseDatabase.getInstance().getReference("settings/districts")

                    // Push the data to Fire base cloud data store
                    ref.push().setValue(districtObj)

                    // Clear registration form after saving advance
                    txtDistrict?.setText("")

                    // Display response message after saving district
                    Toast.makeText(applicationContext,
                        "Successfully saved district", Toast.LENGTH_SHORT).show()

                    // Close dialog after saving
                    dialog.dismiss()

                } else {
                    dialog.dismiss()
                }
            }

            builder.setNegativeButton(android.R.string.no) { dialog, _ ->
                // close dialog
                dialog.dismiss()
            }
            builder.show()
        }

        /************************** End of Start PricePerKg dialog *************************/



    }

    // Method to capitalize every first letter in word: extend String class
    private fun String.toTitleCase(): String = split(" ").map { it.capitalize() }.joinToString(" ")






        // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
