package com.codepoint.villagefarms

import DistrictMarketAdapter
import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.codepoint.villagefarms.models.District
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_district_market_list.*


class DistrictMarketListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_district_market_list)

        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = ("Markets by District")

        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        val recyclerView = market_district_list_recycler_view

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


        // Get a reference to the district list
        val ref = FirebaseDatabase.getInstance().getReference("settings/districts")

        // Attach a listener to read the data at our farmers reference
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val districtList = ArrayList<District>()

                // Eliminates duplicate list rows when child added or removed in Firebase
                districtList.clear()

                for (ds in dataSnapshot.children) {
                    val district = ds.getValue(District::class.java)

                    // Extract object ID key from Fire base and assign to arrayList
                    district?.objectId = ds.key

                    districtList.add(district!!)
                    Log.d(ContentValues.TAG, district.toString())
                }

                // specify an adapter
                var adapter = DistrictMarketAdapter(districtList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        /************************** End of RecyclerView Adapter *************************/


    }

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}