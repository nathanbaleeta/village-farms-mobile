package com.codepoint.villagefarms

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {

    // Declare Views
    lateinit var txtFirstname: EditText
    lateinit var txtLastname: EditText
    lateinit var spTitle: Spinner

    lateinit var rgSex: RadioGroup
    lateinit var rbMale: RadioButton
    lateinit var rbFemale: RadioButton

    lateinit var spMaritalStatus: Spinner
    lateinit var spDistrict: Spinner
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
        spTitle = findViewById(R.id.spTitle)

        rgSex = findViewById(R.id.rg_sex)
        rbMale = findViewById(R.id.rb_male)
        rbFemale = findViewById(R.id.rb_female)

        spMaritalStatus = findViewById(R.id.spMaritalStatus)
        spDistrict = findViewById(R.id.spDistrict)
        txtPhone = findViewById(R.id.txtPhone)
        txtYearOpened = findViewById(R.id.txtYearOpened)
        txtMatureTrees = findViewById(R.id.txtMatureTrees)
        txtImmatureTrees = findViewById(R.id.txtImmatureTrees)
        txtHectarage = findViewById(R.id.txtHectarage)

        /***************** Title options Spinner ****************/

        // Initializing a String Array
        val titleList = resources.getStringArray(R.array.titleList)

        // Initializing an ArrayAdapter
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, titleList)

        // Finally, data bind the spinner object with adapter
        spTitle.adapter = adapter
        spTitle.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spTitle.setSelection(position)
            }
        }

        /***************** Title options Spinner ****************/


        /***************** Marital Status options Spinner ****************/

        // Initializing a String Array
        val maritalStatusList = resources.getStringArray(R.array.maritalStatusList)

        // Initializing an ArrayAdapter
        var maritalStatusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, maritalStatusList)

        // Finally, data bind the spinner object with adapter
        spMaritalStatus.adapter = maritalStatusAdapter
        spMaritalStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spMaritalStatus.setSelection(position)
            }
        }

        /***************** Marital Status options Spinner ****************/

        /***************** District options Spinner ****************/

        // Initializing a String Array
        val districtList = resources.getStringArray(R.array.districtList)

        // Initializing an ArrayAdapter
        var districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, districtList)

        // Finally, data bind the spinner object with adapter
        spDistrict.adapter = districtAdapter
        spDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spDistrict.setSelection(position)
            }
        }

        /***************** District Status options Spinner ****************/


        /***************** Year established Date picker ****************/
        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd" // mention the format you need
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
            var sex = ""
            if (rgSex.checkedRadioButtonId != -1){
                if (rbMale.isChecked){
                    sex += "Male"
                } else if (rbFemale.isChecked){
                    sex += "Female"
                }
            }
            saveFarmer(sex)
        }
    }

    private fun saveFarmer(sex:String) {
        val firstname = txtFirstname.text.toString().capitalize().trim()
        val lastname = txtLastname.text.toString().capitalize().trim()
        val title = spTitle.selectedItem.toString().capitalize().trim()
        val maritalStatus = spMaritalStatus.selectedItem.toString().capitalize().trim()
        val district = spDistrict.selectedItem.toString().capitalize().trim()
        val phone = txtPhone.text.toString().trim()
        val yearOpened = txtYearOpened.text.toString().trim()

        // Capture datetime when expense was created and store in created
        val sdf = SimpleDateFormat("dd/M/yyyy, hh:mm:ss")
        val created = sdf.format(Date())

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
        }  else if (phone.isEmpty()) {
            txtPhone.error = "Please enter a phone number"
            return
        } else if (yearOpened.isEmpty()) {
            txtYearOpened.error = "Please enter a date"
            return
        }  else if (matureTrees == null) {
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
            val farmer = Farmer(
                firstname,
                lastname,
                title,
                sex,
                maritalStatus,
                district,
                phone,
                yearOpened,
                matureTrees,
                immatureTrees,
                hectarage,
                created
            )

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