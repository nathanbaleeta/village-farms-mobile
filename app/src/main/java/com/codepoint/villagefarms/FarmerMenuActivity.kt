package com.codepoint.villagefarms

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast


class FarmerMenuActivity : AppCompatActivity() {
    // Declare as global variables
    private var objectId:String? = null
    private var firstName:String? = null
    private var lastName:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_menu)

        // Get data from intent
        val intent = intent
        objectId = intent.getStringExtra("objectId")
        firstName = intent.getStringExtra("firstName")
        lastName = intent.getStringExtra("lastName")


        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = firstName.plus(" ").plus(lastName)

        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)



    }

    // Inflate the menu; this adds items to the action bar if it is present.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override  // Handle action bar item clicks here.
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_advances) {
            val intent = Intent(this, FarmerAdvancesActivity::class.java)
            intent.putExtra("objectId", objectId)
            intent.putExtra("firstName", firstName)
            intent.putExtra("lastName", lastName)
            startActivity(intent)

        }
        if (id == R.id.action_procurements) {
            Toast.makeText(this, "Procurements Clicked", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }



    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
