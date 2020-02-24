package com.codepoint.villagefarms

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.codepoint.villagefarms.fragments.AdvancesFragment
import com.codepoint.villagefarms.fragments.FarmerFragment
import com.codepoint.villagefarms.fragments.ProcurementFragment
import com.codepoint.villagefarms.fragments.SalesFragment
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_farmers -> {

                val farmerFragment = FarmerFragment.newInstance()
                openFragment(farmerFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_sales -> {

                val salesFragment = SalesFragment.newInstance()
                openFragment(salesFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        // Set default BottomNavigationView tab
        val farmerFragment = FarmerFragment.newInstance()
        openFragment(farmerFragment)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }
}