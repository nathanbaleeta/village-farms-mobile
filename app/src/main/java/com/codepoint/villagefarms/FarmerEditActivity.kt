package com.codepoint.villagefarms

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.*
import com.codepoint.villagefarms.models.Farmer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_register.*
import android.widget.Toast
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.R.string.no
import android.R.attr.name
import android.app.DatePickerDialog
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.text.SimpleDateFormat
import java.util.*


class FarmerEditActivity : AppCompatActivity() {

    // Declare Views
    lateinit var spDistrict: Spinner
    lateinit var spTA: Spinner

    lateinit var txtPhone: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_edit)


        // Get data from intent
        val intent = intent
        val objectId = intent.getStringExtra("objectId")
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")


        //actionbar
        val actionbar = supportActionBar


        //to change title of activity programmatically to full name
        this.title = firstName.plus(" ").plus(lastName)

        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize Views
        spDistrict = findViewById(R.id.spDistrict)
        spTA = findViewById(R.id.spTraditionalAuthority)


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

        /***************** TA options Spinner **************************************/
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


        /* ------------------Initializing an ArrayAdapters for TA--------------- */

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

        /* ------------------Initializing an ArrayAdapters for TA--------------- */

        /***************** TA options Spinner **************************************/


        /***************** District options Spinner *********************************/
        // Initializing a String Array
        val districtList = resources.getStringArray(R.array.districtList)

        // Initializing an ArrayAdapter for district
        var districtAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, districtList)

        // Finally, data bind the spinner object with adapter
        spDistrict.adapter = districtAdapter

        /* ------------------Finally, data bind the spinner TA spinner object with adapter --------------- */

        // Set spinner to value retrieved from Fire base: set selected item of spinner by value, not by position
        if (farmer.district != null) {
            val spinnerPosition = districtAdapter.getPosition(farmer.district)
            spDistrict.setSelection(spinnerPosition)

            when (farmer.district) {
                // Data bind TA list based on district retrieved from firebase
                "Chitipa" ->
                    spTA.adapter = chitipaAdapter

                "Ntchisi" ->
                    spTA.adapter = ntchisiAdapter
                "Nkhatabay" ->
                    spTA.adapter = nkhatabayAdapter
                "Rumphi" ->
                    spTA.adapter = rumphiAdapter
                "Mzimba" ->
                    spTA.adapter = mzimbaAdapter

                else -> { // Note the block
                    // do nothing
                }
            }

        }


        /* ------------------Finally, data bind the spinner TA spinner object  with adapter --------------- */

        if (farmer.district != null) {
            val spinnerPosition = districtAdapter.getPosition(farmer.district)
            spDistrict.setSelection(spinnerPosition)

            when (farmer.district) {
                // activate TA list based on input
                "Chitipa" ->
                    spTA.setSelection(chitipaAdapter.getPosition(farmer.traditionalAuthority))
                "Ntchisi" ->
                    spTA.setSelection(ntchisiAdapter.getPosition(farmer.traditionalAuthority))
                "Nkhatabay" ->
                    spTA.setSelection(nkhatabayAdapter.getPosition(farmer.traditionalAuthority))
                "Rumphi" ->
                    spTA.setSelection(rumphiAdapter.getPosition(farmer.traditionalAuthority))
                "Mzimba" ->
                    spTA.setSelection(mzimbaAdapter.getPosition(farmer.traditionalAuthority))

                else -> { // Note the block
                    // do nothing
                }
            }

        }


        /* ------------------ Event listener for district spinner --------------- */


        // District spinner avoids onItemSelectedListener since it listens to
        // activity initialization events and affects data quality. So instead,
        // uses onTouchListener and embeds onItemSelectedListener
        spDistrict.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {

                    /* District Spinner event listener uses onTouchListener and embeds onItemSelectedListener  */

                    spDistrict.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
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
                                    spTA.adapter = chitipaAdapter

                                    /* Use event listener to listen and set TA position to any from Chitipa */
                                    spTA.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                            }

                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                // Set value for district
                                                spTA.setSelection(position)



                                            }
                                        }
                                    /* Use event listener to listen and set TA position to any from Chitipa */

                                } else if (spinnerValue == "Ntchisi") {
                                    spTA.adapter = ntchisiAdapter

                                    /* Use event listener to listen and set TA position to any from Ntchisi */
                                    spTA.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                            }

                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                // Set value for district
                                                spTA.setSelection(position)



                                            }
                                        }
                                    /* Use event listener to listen and set TA position to any from Ntchisi */

                                } else if (spinnerValue == "Nkhatabay") {
                                    spTA.adapter = nkhatabayAdapter

                                    /* Use event listener to listen and set TA position to any from Nkhatabay */
                                    spTA.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                            }

                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                // Set value for district
                                                spTA.setSelection(position)



                                            }
                                        }
                                    /* Use event listener to listen and set TA position to any from Nkhatabay */

                                } else if (spinnerValue == "Rumphi") {
                                    spTA.adapter = rumphiAdapter

                                    /* Use event listener to listen and set TA position to any from Rumphi */
                                    spTA.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                            }

                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                // Set value for district
                                                spTA.setSelection(position)



                                            }
                                        }
                                    /* Use event listener to listen and set TA position to any from Rumphi */

                                } else if (spinnerValue == "Mzimba") {
                                    spTA.adapter = mzimbaAdapter

                                    /* Use event listener to listen and set TA position to any from Mzimba */
                                    spTA.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                            }

                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                // Set value for district
                                                spTA.setSelection(position)



                                            }
                                        }
                                    /* Use event listener to listen and set TA position to any from Mzimba */

                                }


                            }
                        }


                    /* District Spinner event listener uses onTouchListener and embeds onItemSelectedListener  */


                }
                return false
            }
        })


        /* ------------------ Event listener for district spinner --------------- */


        /***************** District options Spinner *********************************/

        /************************** Set phone **************************************/

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
        /************************************************************************/

        // Text view for Year opened
        val opened = findViewById<TextView>(R.id.txtYearOpened)

        //setText for year opened
        opened.text = farmer.yearOpened


        /***************** Year established Date picker ****************/
        // Calendar set to the current date
        var cal = Calendar.getInstance()
        

        //rollback 90 days
        //cal.add(Calendar.DAY_OF_YEAR, -90)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                //val date = sdf.format
                val date = sdf.format(farmer.yearOpened)


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
            //dialog.datePicker.maxDate = System.currentTimeMillis() -  7776000000
            dialog.show()
        }


        /***************** Year established Date picker *************************/


        /************************** Set Year Opened *****************************/
        /************************************************************************/

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
