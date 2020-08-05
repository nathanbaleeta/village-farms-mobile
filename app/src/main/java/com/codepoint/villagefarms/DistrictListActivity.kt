package com.codepoint.villagefarms

import DistrictAdapter
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
import com.codepoint.villagefarms.models.District
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_district_list.*
import kotlinx.android.synthetic.main.district_dialog.*
import kotlinx.android.synthetic.main.district_dialog.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList




class DistrictListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_district_list)


        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = ("Districts")

        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        val recyclerView = settings_district_list_recycler_view

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
        val ref = FirebaseDatabase.getInstance().getReference("settings").child("districts")

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

        /************************** Start District dialog ****************************/


        // Add district by inflating district dialog builder
        fabAddDistrict.setOnClickListener {
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.district_dialog, null)

            val builder = AlertDialog.Builder(this)
            builder.setView(mDialogView)
            builder.setTitle("Add District")
            builder.setPositiveButton(android.R.string.yes) { dialog, _ ->

                //get text from EditTexts of custom layout
                val district = mDialogView.txtDistrict.text.toString().toLowerCase().toTitleCase().trim()

                // Capture datetime when expense was created and store in created
                val sdf = SimpleDateFormat("dd/MM/yyyy, hh:mm:ss")
                val created = sdf.format(Date())

                if(district.isNotEmpty()) {

                    val districtObj = District(
                        district,
                        created,
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

        /************************** End of  District dialog *************************/



    }

    // Method to capitalize every first letter in word: extend String class
    private fun String.toTitleCase(): String = split(" ").map { it.capitalize() }.joinToString(" ")



        // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
