package com.codepoint.villagefarms

import AdvancesAdapter
import DistrictAdapter
import android.content.ContentValues
//import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.codepoint.villagefarms.models.Advance
import com.codepoint.villagefarms.models.District
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_farmer_advances.*


class DistrictListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_district_list)


        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = ("Districts")

        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        val recyclerView = farmer_advances_list_recycler_view

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
                var adapter = DistrictAdapter(districtList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        /************************** End of RecyclerView Adapter *************************/

        // Start DistrictAddTA activity from DistrictList Activity
        fabAddAdvance.setOnClickListener {
            //val intent = Intent(this, FarmerAddAdvanceActivity::class.java)
            //.putExtra("objectId", objectId)
            //this?.startActivity(intent)
        }



    }


    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
