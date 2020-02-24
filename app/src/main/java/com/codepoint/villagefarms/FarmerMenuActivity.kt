package com.codepoint.villagefarms

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast


class FarmerMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_menu)

        // Get data from intent
        val intent = intent
        val objectId = intent.getStringExtra("objectId")
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")


        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = firstName.plus(" ").plus(lastName)

        //set back button
        actionBar!!.setDisplayHomeAsUpEnabled(true)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.action_advances) {
            Toast.makeText(this, "Advances", Toast.LENGTH_LONG).show()
            return true
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
