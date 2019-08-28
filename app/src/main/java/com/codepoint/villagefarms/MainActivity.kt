package com.codepoint.villagefarms

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.codepoint.villagefarms.Fragments.AdvancesFragment
import com.codepoint.villagefarms.Fragments.FarmerFragment
import com.codepoint.villagefarms.Fragments.ProcurementFragment
import com.codepoint.villagefarms.Fragments.SalesFragment

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_farmers -> {

                val farmerFragment = FarmerFragment.newInstance()
                openFragment(farmerFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_advances -> {

                val advancesFragment = AdvancesFragment.newInstance()
                openFragment(advancesFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_procurements -> {

                val procurementFragment = ProcurementFragment.newInstance()
                openFragment(procurementFragment)
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

        // Set default BottomNavigationView tab
        val farmerFragment = FarmerFragment.newInstance()
        openFragment(farmerFragment)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }
}