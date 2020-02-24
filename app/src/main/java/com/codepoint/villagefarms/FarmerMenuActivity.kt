package com.codepoint.villagefarms

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class FarmerMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_menu)

        val navView: BottomNavigationView = findViewById(R.id.nav_view2)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
