package com.codepoint.villagefarms

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RadioListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_list)

        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        val radioGroup = RadioGroup(this)
        val radioGroup2 = RadioGroup(this)
        val textView = TextView(this)
        textView.text = "option 1 is selected"
        radioGroup.orientation = RadioGroup.VERTICAL

        //This option will be the default value
        val radioButtonDefault = RadioButton(this)
        radioButtonDefault.text = "Default"
        radioGroup.addView(radioButtonDefault)
        radioGroup.check(radioButtonDefault.id)

        val districtList = ArrayList<String>()

        // Get a reference to our farmer's advances
        val refDistricts = FirebaseDatabase.getInstance()
            .getReference("settings/districts")

        // Attach a listener to read the data at our settings/districts reference
        refDistricts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Eliminates duplicate list rows when child added or removed in Firebase
                districtList.clear()
                for (ds in dataSnapshot.children) {
                    val districtName: String? = ds.child("district").getValue(String::class.java)
                    districtList.add(districtName!!)

                }

                Log.d(ContentValues.TAG, districtList.toString())
                for(option in districtList){
                    val radioButton = RadioButton(this@RadioListActivity)
                    radioButton.text = option
                    radioGroup.addView(radioButton)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        /*val districtList = ArrayList<String>()
        districtList.add("Kampala")
        districtList.add("Jinja")
        districtList.add("Mbale")
        districtList.add("Mbarara")
        districtList.add("Fort Portal")


        Log.d(ContentValues.TAG, districtList.toString())
*/
        //These options will be the regular radio buttons
        val options = arrayOf("Kampala", "Jinja", "Mbarara", "Mbale", "Arua")






        //This option can't be unchecked
        val radioButtonAlwaysChecked = RadioButton(this)
        radioButtonAlwaysChecked.text ="Always Checked"
        radioButtonAlwaysChecked.isChecked = true
        radioGroup2.addView(radioButtonAlwaysChecked)
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            textView.text = "option "+i+" is selected"
        })
        linearLayout.addView(radioGroup)
        linearLayout.addView(radioGroup2)
        linearLayout.addView(textView)
    }
}