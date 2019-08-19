package com.codepoint.villagefarms

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {

    // Declare Views
    lateinit var txtFirstname: EditText
    lateinit var txtLastname: EditText
    lateinit var txtPhone: EditText
    lateinit var txtYearOpened: EditText
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
        txtYearOpened = findViewById(R.id.txtYearOpened)
        txtMatureTrees = findViewById(R.id.txtMatureTrees)
        txtImmatureTrees = findViewById(R.id.txtImmatureTrees)
        txtHectarage = findViewById(R.id.txtHectarage)


        /***************** Year established Date picker ****************/
        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd MMM, yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val date = sdf.format(cal.time)

            // Display Selected date in textbox
            txtYearOpened.setText(date)

        }

        txtYearOpened.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        /***************** Year established Date picker ****************/

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
        val opened = txtYearOpened.text.toString().trim()


        // Implement Number format exception in try catch blocks to avoid app crashing
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
            val farmer = Farmer(firstname, lastname, phone, opened, matureTrees, immatureTrees, hectarage)

            // Support offline data entry by enabling disk persistence
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
            val ref = FirebaseDatabase.getInstance().getReference("farmer")

            // Push the data to Fire base cloud data store
            ref.push().setValue(farmer)

            // Clear registration form after saving farmer
            txtFirstname.setText("")
            txtLastname.setText("")
            txtPhone.setText("")
            txtYearOpened.setText("")
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