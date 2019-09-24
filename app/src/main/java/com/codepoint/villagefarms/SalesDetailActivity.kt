package com.codepoint.villagefarms

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.codepoint.villagefarms.models.Sale
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SalesDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_detail)

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
        val saleReference = FirebaseDatabase.getInstance().getReference("sales").child(objectId)

        // Attach a listener to read the data at our sales object reference
        val saleListener = object : ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {

                // Get Sale object and use the values to update the UI
                val sale = ds.getValue(Sale::class.java)

                // Extract object ID key from Fire base and update object
                sale?.objectId = ds.key


                return populateUI(sale!!)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Sale failed, log a message
                println("The read failed: " + databaseError.toException())
                // ...
            }
        }
        saleReference.addValueEventListener(saleListener)

        /************************** Add Event Listener object *************************/


    }

    private fun populateUI(sale: Sale) {

        // Text view for full name
        val fullName = findViewById<TextView>(R.id.info_text_fullName)

        //setText by concatenating firstName and lastName
        fullName.text = sale.firstname.plus(" ").plus(sale.lastname)



        // Text view for title
        val title = findViewById<TextView>(R.id.info_text_Title)





    }

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
