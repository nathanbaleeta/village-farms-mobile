package com.codepoint.villagefarms

import AdvancesAdapter
import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.codepoint.villagefarms.models.Advance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_farmer_advances.*
import kotlinx.android.synthetic.main.fragment_farmer.*


class FarmerAdvancesActivity : AppCompatActivity() {

    // Declare as global variables
    private var objectId:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_farmer_advances)

        // Get data from intent
        val intent = intent
        objectId = intent.getStringExtra("objectId")

        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = ("Advances")

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
        val ref = FirebaseDatabase.getInstance().getReference("advances/$objectId")


        // Attach a listener to read the data at our farmers reference
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val advancesList = ArrayList<Advance>()

                // Eliminates duplicate list rows when child added or removed in Firebase
                advancesList.clear()

                for (ds in dataSnapshot.children) {
                    val advance = ds.getValue(Advance::class.java)

                    // Extract object ID key from Fire base and assign to arrayList
                    advance?.objectId = ds.key

                    advancesList.add(advance!!)
                    Log.d(ContentValues.TAG, advance.toString())

                }

                // specify an adapter
                var adapter = AdvancesAdapter(advancesList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        /************************** End of RecyclerView Adapter *************************/

        // Start FarmerAddAdvance activity from farmer Advances Activity
        fabAddAdvance.setOnClickListener {
            val intent = Intent(this, FarmerAddAdvanceActivity::class.java)
            intent.putExtra("objectId", objectId)
            this?.startActivity(intent)
        }



    }


    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
