package com.codepoint.villagefarms

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Integer.parseInt


class RegisterActivity : AppCompatActivity() {

    // Declare Views
    lateinit var txtFirstname: EditText
    lateinit var txtLastname: EditText
    lateinit var txtPhone: EditText
    lateinit var txtMatureTrees: EditText
    lateinit var txtImmatureTrees: EditText
    lateinit var txtHectarage: EditText
    lateinit var buttonSave: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //actionbar
        val actionbar = supportActionBar

        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize Views
        txtFirstname = findViewById(R.id.txtFirstname)
        txtLastname = findViewById(R.id.txtLastname)
        txtPhone = findViewById(R.id.txtPhone)
        txtMatureTrees = findViewById(R.id.txtMatureTrees)
        txtImmatureTrees = findViewById(R.id.txtImmatureTrees)
        txtHectarage = findViewById(R.id.txtHectarage)
        buttonSave = findViewById(R.id.btnSaveFarmer)

        // Attach a click listener to save button
        buttonSave.setOnClickListener {
            saveFarmer()
        }

    }

    private fun saveFarmer() {
        val firstname = txtFirstname.text.toString().trim()
        val lastname = txtLastname.text.toString().trim()
        val phone = txtPhone.text.toString().trim()

        // Implement Number format exception in try catch blocks
        val matureTrees: Int? = try {
            parseInt(txtMatureTrees.text.toString())
        } catch (e: NumberFormatException) {
            null
        }

        val immatureTrees: Int? = try {
            parseInt(txtImmatureTrees.text.toString())
        } catch (e: NumberFormatException) {
            null
        }

        val hectarage: Int? = try {
            parseInt(txtHectarage.text.toString())
        } catch (e: NumberFormatException) {
            null
        }
        //val matureTrees = txtMatureTrees.text.toString().toInt()
        //val immatureTrees = txtImmatureTrees.text.toString().toInt()
        //val hectarage = txtHectarage.text.toString().toInt()


        // Validate registration form before saving to Firebase database
        if (firstname.isEmpty()) {
            txtFirstname.error = "Please enter a first name"
            return
        } else if (lastname.isEmpty()) {
            txtLastname.error = "Please enter a Last name"
            return
        } else if (phone.isEmpty()) {
            txtPhone.error = "Please enter a phone number"
            return
        } else if (matureTrees == null) {
            txtMatureTrees.error = "Please enter a digit"
            return
        } else if (immatureTrees == null) {
            txtImmatureTrees.error = "Please enter a digit"
            return
        } else if (hectarage == null) {
            txtHectarage.error = "Please enter a digit"
            return
        }
        else {
            // Instantiate new farmer using Farmer data class model
            val farmer = Farmer(firstname, lastname, phone, matureTrees, immatureTrees, hectarage)

            // Support offline data entry by enabling disk persistence
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
            val ref = FirebaseDatabase.getInstance().getReference("farmer")

            // Push the data to Fire base cloud data store
            ref.push().setValue(farmer)

            // Clear registration form after saving farmer
            txtFirstname.setText("")
            txtLastname.setText("")
            txtPhone.setText("")
            txtMatureTrees.setText("")
            txtImmatureTrees.setText("")
            txtHectarage.setText("")

            // Display response message after saving farmer
            Snackbar.make(scroll_layout, "Farmer was successfully registered", Snackbar.LENGTH_LONG)
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