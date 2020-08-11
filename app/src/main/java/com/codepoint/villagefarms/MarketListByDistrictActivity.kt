package com.codepoint.villagefarms

import MarketPriceAdapter
import PriceAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.codepoint.villagefarms.models.Market
import com.codepoint.villagefarms.models.Price
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_market_list_by_district.*
import kotlinx.android.synthetic.main.activity_price_list.*

class MarketListByDistrictActivity : AppCompatActivity() {


    // Declare as global variables
    private var objectId:String? = null
    private var district:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_list_by_district)

        // Get data from intent
        val intent = intent
        objectId = intent.getStringExtra("objectId")
        district = intent.getStringExtra("district")

        //actionbar
        val actionbar = supportActionBar

        //to change title of activity programmatically to full name
        this.title = district.plus(" ").plus("Market list")

        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)


        val recyclerView = market_price_list_recycler_view
        recyclerView.setHasFixedSize(true)

        // Use a linear layout manager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        // Divider in-built item decoration
        recyclerView!!.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        /************************** RecyclerView Adapter *************************/

        // Get a reference to our farmer's advances
        val ref = FirebaseDatabase.getInstance().getReference("markets/$objectId")

        // Attach a listener to read the data at our farmers reference
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val marketPriceList = ArrayList<Market>()

                // Eliminates duplicate list rows when child added or removed in Firebase
                marketPriceList.clear()

                for (ds in dataSnapshot.children) {
                    val market = ds.getValue(Market::class.java)

                    marketPriceList.add(market!!)
                }

                // specify an adapter
                var adapter = MarketPriceAdapter(marketPriceList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        /************************** End of RecyclerView Adapter *************************/

        /************************** Start Market Price Add activity ****************************/


        // Add market price event handler to floating button
        fabAddMarketPrice.setOnClickListener {
            val intent = Intent(this, MarketPriceAddActivity::class.java)
            intent.putExtra("objectId", objectId)
            intent.putExtra("district",district)
            this?.startActivity(intent)

        }

        /************************** End of Market Price Add activity *************************/


        // fabAddMarketPrice

    }
    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}