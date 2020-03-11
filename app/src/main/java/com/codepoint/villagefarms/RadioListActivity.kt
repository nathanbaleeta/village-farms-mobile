package com.codepoint.villagefarms

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RadioListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_list)


        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        val radioGroup = RadioGroup(this@RadioListActivity)
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

                //Log.d(ContentValues.TAG, districtList.toString())
                for(option in districts){
                    val radioButton = RadioButton(this@RadioListActivity)
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






    }


}

