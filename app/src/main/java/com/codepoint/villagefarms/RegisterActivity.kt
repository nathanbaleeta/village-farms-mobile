package com.codepoint.villagefarms

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(){

    // Declare Views
    lateinit var txtFirstname : EditText
    lateinit var txtLastname : EditText
    lateinit var buttonSave : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //actionbar
        val actionbar = supportActionBar

        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize Views
        txtFirstname =  findViewById(R.id.txtFirstname)
        txtLastname =  findViewById(R.id.txtLastname)
        buttonSave =  findViewById(R.id.btnSaveFarmer)

        // Attach a click listener to save button
        buttonSave.setOnClickListener {
            saveFarmer()
        }

    }

    private fun saveFarmer(){
        val firstname = txtFirstname.text.toString().trim()
        val lastname = txtLastname.text.toString().trim()

        // Validate registration form before saving to Firebase database
        if (firstname.isEmpty()) {
            txtFirstname.error = "Please enter a first name"
            return
        } else if (lastname.isEmpty()) {
            txtLastname.error = "Please enter a Last name"
            return
        }
        else {
            // Instantiate new farmer using Farmer data class model
            val farmer = Farmer(firstname, lastname)
            val ref =  FirebaseDatabase.getInstance().getReference("farmer")

            // Support offline data entry of field operations
            ref.keepSynced(true);

            // Push the data to Fire base cloud data store
            ref.push().setValue(farmer)

            // Clear registration form after saving farmer
            txtFirstname.setText("");
            txtLastname.setText("");

            // Display response message after saving farmer
            Snackbar.make( scroll_layout ,"Farmer was successfully registered", Snackbar.LENGTH_LONG)
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