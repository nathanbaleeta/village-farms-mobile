package com.codepoint.villagefarms

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.*
import com.codepoint.villagefarms.models.Farmer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener




class FarmerEditActivity : AppCompatActivity() {

    // Declare Views
    lateinit var spDistrict: Spinner

    lateinit var txtPhone: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_edit)


        // Get data from intent
        val intent = intent
        val objectId = intent.getStringExtra("objectId")
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        println(objectId)

        //actionbar
        val actionbar = supportActionBar


        //to change title of activity programmatically to full name
        this.title = firstName.plus(" ").plus(lastName)

        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize Views
        spDistrict = findViewById(R.id.spDistrict)

        txtPhone = findViewById(R.id.txtPhone)



        /***************** Verify phone number doesn't exceed 12 digits  ****************/
        txtPhone.setFilters(
            arrayOf<InputFilter>(
                InputFilter.LengthFilter(12)

            )
        )
        /***************** Verify phone number doesn't exceed 12 digits ****************/

        /************************** Add Event Listener object *************************/

        // Get a reference to given sales object
        val farmerReference = FirebaseDatabase.getInstance().getReference("farmers").child(objectId)

        // Attach a listener to read the data at our sales object reference
        val farmerListener = object : ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {

                // Get Sale object and use the values to update the UI
                val farmer = ds.getValue(Farmer::class.java)

                // Extract object ID key from Fire base and update object
                farmer?.objectId = ds.key



                return populateUI(farmer!!)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Sale failed, log a message
                println("The read failed: " + databaseError.toException())
                // ...
            }
        }
        farmerReference.addValueEventListener(farmerListener)

        /************************** Add Event Listener object *************************/


    }

    private fun populateUI(farmer: Farmer) {

        /************************** Set first name *************************/

        // Text view for first name
        val firstname = findViewById<TextView>(R.id.txtFirstname)

        //setText for first name
        firstname.text = farmer.firstname

        /************************** Set first name *************************/

        /************************** Set last name *************************/

        // Text view for last name
        val lastname = findViewById<TextView>(R.id.txtLastname)

        //setText for last name
        lastname.text = farmer.lastname

        /************************** Set last name *************************/


        /************************** Set Title *************************/

        // Text view for title
        val title = findViewById<RadioGroup>(R.id.rg_title)

        // when replaces the switch operator of C-like languages: supports multiple cases
        when (farmer.title) {
            // switch on/ off correct button based on input
            "Mr" -> title.check(R.id.rb_mr)
            "Mrs" -> title.check(R.id.rb_mrs)
            "Ms" -> title.check(R.id.rb_miss)
            else -> { // Note the block
                // do nothing
            }
        }

        /************************** Set Title *************************/

        /************************** Set Gender *************************/

        // Text view for sex
        val gender = findViewById<RadioGroup>(R.id.rg_sex)

        // when replaces the switch operator of C-like languages: supports multiple cases
        when (farmer.sex) {
            // switch on/ off correct button based on input
            "Male" -> gender.check(R.id.rb_male)
            "Female" -> gender.check(R.id.rb_female)
            else -> { // Note the block
                // do nothing
            }
        }

        /************************** Set Gender *************************/

        /************************** Set Marital status *************************/

        // Text view for Marital status group
        val rgMaritalStatus = findViewById<RadioGroup>(R.id.rg_maritalStatus)

        // when replaces the switch operator of C-like languages: supports multiple cases
        when (farmer.maritalStatus) {
            // switch on/ off correct button based on input
            "Married" -> rgMaritalStatus.check(R.id.rb_married)
            "Single" -> rgMaritalStatus.check(R.id.rb_single)
            "Widowed" -> rgMaritalStatus.check(R.id.rb_widowed)
            "Separated" -> rgMaritalStatus.check(R.id.rb_separated)
            else -> { // Note the block
                // do nothing
            }
        }

        /************************** Set Marital status *************************/

        /***************** District options Spinner *********************************/
        // Initializing a String Array
        val districtList = resources.getStringArray(R.array.districtList)

        // Initializing an ArrayAdapter for district
        var districtAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, districtList)

        // Finally, data bind the spinner object with adapter
        spDistrict.adapter = districtAdapter

        // Set district to value retrieved from Fire base
        // Set selected item of spinner by value, not by position
        if (farmer.district != null) {
            val spinnerPosition = districtAdapter.getPosition(farmer.district)
            spDistrict.setSelection(spinnerPosition)
        }


        /***************** District options Spinner *********************************/

        /************************** Set phone *****************************/

        // Text view for pone
        val phone = findViewById<TextView>(R.id.txtPhone)

        //setText for phone
        phone.text = farmer.phone

        /************************** Set phone ***********************************/

        /************************** Set MM Registered *************************/

        // Text view for Marital status group
        val rgMMRegistered = findViewById<RadioGroup>(R.id.rg_MMRegistered)

        // when replaces the switch operator of C-like languages: supports multiple cases
        when (farmer.mmRegistered) {
            // switch on/ off correct button based on input
            "Yes" -> rgMMRegistered.check(R.id.rb_registered_yes)
            "No" -> rgMMRegistered.check(R.id.rb_registered_yes)

            else -> { // Note the block
                // do nothing
            }
        }

        /************************** Set MM Registered *************************/

        /************************** Set MM Payments ***************************/

        // Text view for Marital status group
        val rgMMPayments = findViewById<RadioGroup>(R.id.rg_MMPayment)

        // when replaces the switch operator of C-like languages: supports multiple cases
        when (farmer.mmPayment) {
            // switch on/ off correct button based on input
            "Yes" -> rgMMPayments.check(R.id.rb_payment_yes)
            "No" -> rgMMPayments.check(R.id.rb_payment_no)

            else -> { // Note the block
                // do nothing
            }
        }

        /************************** Set MM Payments ****************************/

        /************************** Set Year Opened *****************************/

        // Text view for Year opened
        val opened = findViewById<TextView>(R.id.txtYearOpened)

        //setText for year opened
        opened.text = farmer.yearOpened

        /************************** Set Year Opened *****************************/

        /************************** Set Acreage *********************************/

        // Text view for Acreage
        val acreage = findViewById<TextView>(R.id.txtAcreage)

        //setText for acreage view: do nullability checking as well
        acreage.text = farmer.acreage.toString()

        /************************** Set Acreage ********************************/

        /************************** Set Year 1 *********************************/

        // Text view for Year 1
        val year1 = findViewById<TextView>(R.id.txtYear1)

        //setText for year 1 view: do nullability checking as well
        year1.text = farmer.year1.toString()

        /************************** Set Year 1 ********************************/

        /************************** Set Year 2 *********************************/

        // Text view for year 2
        val year2 = findViewById<TextView>(R.id.txtYear2)

        //setText for acreage view: do nullability checking as well
        year2.text = farmer.year2.toString()

        /************************** Set Year 2 ********************************/


        /************************** Set Year 3 *********************************/

        // Text view for year 3
        val year3 = findViewById<TextView>(R.id.txtYear3)

        //setText for year 3 view: do nullability checking as well
        year3.text = farmer.year3.toString()

        /************************** Set Year 3 ********************************/


    }

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
