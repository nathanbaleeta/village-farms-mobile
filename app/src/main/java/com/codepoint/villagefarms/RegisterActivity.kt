package com.codepoint.villagefarms

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.view.View
import android.widget.*
import com.codepoint.villagefarms.models.Farmer
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat

import java.util.*




class RegisterActivity : AppCompatActivity() {

    // Declare Views
    lateinit var txtFirstname: EditText
    lateinit var txtLastname: EditText

    lateinit var rgTitle: RadioGroup
    lateinit var rbMr: RadioButton
    lateinit var rbMs: RadioButton
    lateinit var rbMrs: RadioButton


    lateinit var rgMaritalStatus: RadioGroup
    lateinit var rbMarried: RadioButton
    lateinit var rbSingle: RadioButton
    lateinit var rbWidowed: RadioButton
    lateinit var rbSeparated: RadioButton

    lateinit var rgSex: RadioGroup
    lateinit var rbMale: RadioButton
    lateinit var rbFemale: RadioButton

    lateinit var rgMMRegistered: RadioGroup
    lateinit var rbRegisteredYes: RadioButton
    lateinit var rbRegisteredNo: RadioButton

    lateinit var rgMMPayment: RadioGroup
    lateinit var rbPaymentYes: RadioButton
    lateinit var rbPaymentNo: RadioButton

    lateinit var spDistrict: Spinner
    lateinit var spTradtionalAuthority: Spinner

    lateinit var txtPhone: EditText
    lateinit var txtYearOpened: EditText
    lateinit var txtMatureTrees: EditText
    lateinit var txtImmatureTrees: EditText
    lateinit var txtHectarage: EditText
    lateinit var txtAcreage: EditText

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

        rgTitle = findViewById(R.id.rg_title)
        rbMr = findViewById(R.id.rb_mr)
        rbMs = findViewById(R.id.rb_miss)
        rbMrs = findViewById(R.id.rb_mrs)

        rgMaritalStatus = findViewById(R.id.rg_maritalStatus)
        rbMarried = findViewById(R.id.rb_married)
        rbSingle = findViewById(R.id.rb_single)
        rbWidowed = findViewById(R.id.rb_widowed)
        rbSeparated = findViewById(R.id.rb_separated)

        rgSex = findViewById(R.id.rg_sex)
        rbMale = findViewById(R.id.rb_male)
        rbFemale = findViewById(R.id.rb_female)

        rgMMRegistered = findViewById(R.id.rg_MMRegistered)
        rbRegisteredYes = findViewById(R.id.rb_registered_yes)
        rbRegisteredNo = findViewById(R.id.rb_registered_no)

        rgMMPayment = findViewById(R.id.rg_MMPayment)
        rbPaymentYes = findViewById(R.id.rb_payment_yes)
        rbPaymentNo = findViewById(R.id.rb_payment_no)

        spDistrict = findViewById(R.id.spDistrict)
        spTradtionalAuthority = findViewById(R.id.spTraditionalAuthority)

        txtPhone = findViewById(R.id.txtPhone)
        txtYearOpened = findViewById(R.id.txtYearOpened)
        txtMatureTrees = findViewById(R.id.txtMatureTrees)
        txtImmatureTrees = findViewById(R.id.txtImmatureTrees)
        txtHectarage = findViewById(R.id.txtHectarage)
        txtAcreage = findViewById(R.id.txtAcreage)


        /***************** Verify phone number doesn't exceed 12 digits  ****************/
        txtPhone.setFilters(
            arrayOf<InputFilter>(
                InputFilter.LengthFilter(12)

            )
        )
        /***************** Verify phone number doesn't exceed 12 digits ****************/

        /***************** District options Spinner ****************/

        // Initializing a String Array
        val districtList = resources.getStringArray(R.array.districtList)

        // Initializing a String Array for Chitipa district
        val chitipaTAList = resources.getStringArray(R.array.district_chitipa)

        // Initializing a String Array for Ntchisi district
        val ntchisiTAList = resources.getStringArray(R.array.district_ntchisi)

        // Initializing a String Array for Nkhatabay district
        val nkhatabayTAList = resources.getStringArray(R.array.district_nkhatabay)

        // Initializing a String Array for Rumphi district
        val rumphiTAList = resources.getStringArray(R.array.district_rumphi)

        // Initializing a String Array for Rumphi district
        val mzimbaTAList = resources.getStringArray(R.array.district_mzimba)

        // Initializing an ArrayAdapter for district
        var districtAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, districtList)

        // Initializing an ArrayAdapter For Chitipa TA list
        var chitipaAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, chitipaTAList)

        // Initializing an ArrayAdapter For Ntchisi TA list
        var ntchisiAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ntchisiTAList)

        // Initializing an ArrayAdapter For Nkhatabay TA list
        var nkhatabayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nkhatabayTAList)

        // Initializing an ArrayAdapter For Rumphi TA list
        var rumphiAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, rumphiTAList)

        // Initializing an ArrayAdapter For Mzimba TA list
        var mzimbaAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mzimbaTAList)

        // Finally, data bind the spinner object with adapter
        //spDistrict.adapter = districtAdapter
        spDistrict.setAdapter(districtAdapter)

        spDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Set value for district
                spDistrict.setSelection(position)

                // Set value for Traditional authority based on district selected
                val spinnerValue = spDistrict.selectedItem.toString()
                if (spinnerValue == "Chitipa") {
                    spTraditionalAuthority.setAdapter(chitipaAdapter)
                    spTradtionalAuthority.setSelection(position)
                } else if (spinnerValue == "Ntchisi") {
                    spTraditionalAuthority.setAdapter(ntchisiAdapter)
                    spTradtionalAuthority.setSelection(position)
                } else if (spinnerValue == "Nkhatabay") {
                    spTraditionalAuthority.setAdapter(nkhatabayAdapter)
                    spTradtionalAuthority.setSelection(position)
                } else if (spinnerValue == "Rumphi") {
                    spTraditionalAuthority.setAdapter(rumphiAdapter)
                    spTradtionalAuthority.setSelection(position)
                }  else if (spinnerValue == "Mzimba") {
                    spTraditionalAuthority.setAdapter(mzimbaAdapter)
                    spTradtionalAuthority.setSelection(position)
                }
            }
        }

        /***************** District Status options Spinner ****************/

        /***************** Year established Date picker ****************/
        // Calendar set to the current date
        var cal = Calendar.getInstance()

        //rollback 90 days
        cal.add(Calendar.DAY_OF_YEAR, -90)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                val date = sdf.format(cal.time)

                // Display Selected date in text input
                txtYearOpened.setText(date)
            }


        txtYearOpened.setOnClickListener {
            val dialog = DatePickerDialog(
                this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            // Date of farm opened cannot be in future; and can't be less than 3 months(=7776000000 ms)
            dialog.datePicker.maxDate = System.currentTimeMillis() -  7776000000
            dialog.show()
        }



        /***************** Year established Date picker ****************/

        buttonSave = findViewById(R.id.btnSaveFarmer)

        // Attach a click listener to save button
        buttonSave.setOnClickListener {

            /***************** Title radio group ****************/
            var title = ""
            if (rgTitle.checkedRadioButtonId != -1) {
                if (rbMr.isChecked) {
                    title += "Mr"
                } else if (rbMs.isChecked) {
                    title += "Ms"
                } else if (rbMrs.isChecked) {
                    title += "Mrs"
                }
            }
            /***************** Title radio group ****************/

            /***************** Sex radio group ****************/
            var sex = ""
            if (rgSex.checkedRadioButtonId != -1) {
                if (rbMale.isChecked) {
                    sex += "Male"
                } else if (rbFemale.isChecked) {
                    sex += "Female"
                }
            }
            /***************** Sex radio group ****************/

            /***************** Marital status radio group ****************/
            var maritalStatus = ""
            if (rgMaritalStatus.checkedRadioButtonId != -1) {
                if (rbMarried.isChecked) {
                    maritalStatus += "Married"
                } else if (rbSingle.isChecked) {
                    maritalStatus += "Single"
                } else if (rbWidowed.isChecked) {
                    maritalStatus += "Widowed"
                } else if (rbSeparated.isChecked) {
                    maritalStatus += "Separated"
                }
            }
            /***************** Marital status radio group ****************/

            /***************** MM Registered radio group ****************/
            var mmRegistered = ""
            if (rgMMRegistered.checkedRadioButtonId != -1) {
                if (rbRegisteredYes.isChecked) {
                    mmRegistered += "Yes"
                } else if (rbRegisteredNo.isChecked) {
                    mmRegistered += "No"
                }
            }
            /***************** MM Registered radio group ****************/

            /***************** MM Payment radio group ****************/
            var mmPayment = ""
            if (rgMMPayment.checkedRadioButtonId != -1) {
                if (rbPaymentYes.isChecked) {
                    mmPayment += "Yes"
                } else if (rbPaymentNo.isChecked) {
                    mmPayment += "No"
                }
            }
            /***************** MM Payment radio group ****************/

            saveFarmer(title, sex, maritalStatus, mmRegistered, mmPayment)
        }
    }

    private fun saveFarmer(
        title: String,
        sex: String,
        maritalStatus: String,
        mmRegistered: String,
        mmPayment: String
    ) {
        val firstname = txtFirstname.text.toString().toLowerCase().capitalize().trim()
        val lastname = txtLastname.text.toString().toLowerCase().capitalize().trim()

        val district = spDistrict.selectedItem.toString().capitalize().trim()
        val tradionalAuthority = spTradtionalAuthority.selectedItem.toString().capitalize().trim()
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

        val acreage: Double? = try {
            parseDouble(txtAcreage.text.toString())
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
        } else if (phone.isEmpty() || phone.length < 10) {
            txtPhone.error = "Please enter a valid phone number"
            return
        } else if (yearOpened.isEmpty()) {
            txtYearOpened.error = "Please enter a date"
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
        } else if (acreage == null) {
            txtAcreage.error = "Please enter a digit"
            return
        } else {
            // Instantiate new farmer using Farmer data class model
            val farmer = Farmer(
                firstname,
                lastname,
                title,
                sex,
                maritalStatus,
                district,
                tradionalAuthority,
                phone,
                mmRegistered,
                mmPayment,
                yearOpened,
                matureTrees,
                immatureTrees,
                hectarage,
                acreage,
                created
            )

            val ref = FirebaseDatabase.getInstance().getReference("farmers")

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
            txtAcreage.setText("")

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