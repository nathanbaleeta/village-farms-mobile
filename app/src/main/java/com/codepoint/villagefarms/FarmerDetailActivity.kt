package com.codepoint.villagefarms

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.codepoint.villagefarms.models.Farmer
import com.codepoint.villagefarms.models.Sale
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FarmerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_detail)

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

                println(farmer)


                return populateUI(farmer!!)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Farmer failed, log a message
                println("The read failed: " + databaseError.toException())
                // ...
            }
        }
        farmerReference.addValueEventListener(farmerListener)

        /************************** Add Event Listener object *************************/


    }

    private fun populateUI(farmer: Farmer) {

        /************************** Set full name *************************/

        // Text view for full name
        val fullName = findViewById<TextView>(R.id.info_text_fullName)

        //setText by concatenating firstName and lastName
        fullName.text = farmer.firstname.plus(" ").plus(farmer.lastname)


        /************************** Set full name *************************/

        /************************** Set Title *************************/
        // Text view for title
        val title = findViewById<TextView>(R.id.info_text_Title)

        //setText for title view
        title.text = farmer.title


        /************************** Set Title *************************/

        /************************** Set Gender *************************/

        // Text view for title
        val gender = findViewById<TextView>(R.id.info_text_Gender)

        //setText for title view
        gender.text = farmer.sex

        /************************** Set Gender *************************/

        /************************** Set Marital status ********************/

        // Text view for title
        val maritalStatus = findViewById<TextView>(R.id.info_text_MaritalStatus)

        //setText for title view
        maritalStatus.text = farmer.maritalStatus

        /************************** Set Marital status *************************/

        /************************** Set Mobile phone ********************/

        // Text view for title
        val mobile = findViewById<TextView>(R.id.info_text_Mobile)

        //setText for title view
        mobile.text = farmer.phone

        /************************** Set Mobile phone *************************/

        /************************** Set MM Registered ********************/

        // Text view for title
        val mmRegistered = findViewById<TextView>(R.id.info_text_MMRegistered)

        //setText for title view
        mmRegistered.text = farmer.mmRegistered

        /************************** Set MM Registered *************************/


        /************************** Set MM Payments ***************************/

        // Text view for title
        val mmPayments = findViewById<TextView>(R.id.info_text_MMPayments)

        //setText for title view
        mmPayments.text = farmer.mmPayment

        /************************** Set MM Payments *************************/

        /************************** Set Acreage ****************************/

        // Text view for title
        val acreage = findViewById<TextView>(R.id.info_text_Acreage)

        //setText for title view
        acreage.text = farmer.acreage.toString()

        /************************** Set Acreage ****************************/



    }

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
