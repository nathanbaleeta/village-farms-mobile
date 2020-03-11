package com.codepoint.villagefarms

import PriceAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager

import android.widget.*
import com.codepoint.villagefarms.models.Price
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_price_list.*
import kotlin.collections.ArrayList


class PricePerKgListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_list)

        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = ("Price Per Kg List")

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
                    //Log.d(ContentValues.TAG, price.toString())

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

        /************************** Start PricePerKg setting activity ****************************/


        // Add district by inflating district dialog builder
        fabAddPricePerKg.setOnClickListener {
            val intent = Intent(this, SetPricePerKgActivity::class.java)
            this?.startActivity(intent)



        }

        /************************** End of Start PricePerKg setting activity *************************/



    }


        // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
