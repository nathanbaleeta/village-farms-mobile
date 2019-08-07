package com.codepoint.villagefarms

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_farmers -> {
                textMessage.setText(R.string.title_farmers)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_advances -> {
                textMessage.setText(R.string.title_advances)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_procurements -> {
                textMessage.setText(R.string.title_procurements)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sales -> {
                textMessage.setText(R.string.title_sales)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // Floating Action Button for farmer activity
        val fab: View = findViewById(R.id.fabFarmer)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Farmer was successfully registered", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()

            // Open register farmer activity from farmer list activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }



    }
}