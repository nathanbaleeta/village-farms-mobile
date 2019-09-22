package com.codepoint.villagefarms.fragments

import FarmerAdapter
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.codepoint.villagefarms.R
import com.codepoint.villagefarms.RegisterActivity
import com.codepoint.villagefarms.models.Farmer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_farmer.*

class FarmerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_farmer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = farmer_list_recycler_view
        recyclerView.setHasFixedSize(true)

        // Use a linear layout manager
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)


        // Divider in-built item decoration
        recyclerView!!.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )


        /************************** RecyclerView Adapter *************************/


        // Get a reference to our posts
        val ref = FirebaseDatabase.getInstance().getReference("farmers")


        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val farmerList = ArrayList<Farmer>()
                val farmers = mutableListOf<Farmer>()

                // Eliminates duplicate list rows when child added or removed in Firebase
                farmerList.clear()

                for (ds in dataSnapshot.children) {
                    val farmer = ds.getValue(Farmer::class.java)

                    // Extract object ID key from Fire base and assign to arrayList
                    farmer?.objectId = ds.key

                    farmerList.add(farmer!!)
                    Log.d(ContentValues.TAG, farmer.toString())

                }

                // specify an adapter
                var adapter = FarmerAdapter(farmerList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        /************************** End of RecyclerView Adapter *************************/

        // Start registration activity from farmer fragment
        fabRegister.setOnClickListener {
            val intent = Intent(activity, RegisterActivity::class.java)
            activity?.startActivity(intent)
        }

    }


    companion object {
        fun newInstance(): FarmerFragment = FarmerFragment()
    }
}



