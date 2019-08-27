package com.codepoint.villagefarms

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.codepoint.villagefarms.Fragments.AdvancesFragment
import com.codepoint.villagefarms.Fragments.FarmerFragment
import com.codepoint.villagefarms.Fragments.ProcurementFragment
import com.codepoint.villagefarms.Fragments.SalesFragment

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar
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
        navView.selectedItemId = R.id.navigation_farmers

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        // Floating Action Button for farmer activity
        //val fab: View = findViewById(R.id.fabFarmer)
        //fab.setOnClickListener { view ->

            // Open register farmer activity from farmer list activity
         //   val intent = Intent(this, RegisterActivity::class.java)
           // startActivity(intent)

        //}
    }
}