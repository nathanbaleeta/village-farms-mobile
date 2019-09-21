package com.codepoint.villagefarms.fragments

import SaleAdapter
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.codepoint.villagefarms.SalesActivity
import kotlinx.android.synthetic.main.fragment_sales.*
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import com.codepoint.villagefarms.models.Sale
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase


class SalesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            com.codepoint.villagefarms.R.layout.fragment_sales,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = sales_list_recycler_view
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
        val ref = FirebaseDatabase.getInstance().getReference("sales")

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val salesList = ArrayList<Sale>()
                 //val salesList = mutableListOf<Sale>()

                // Eliminates duplicate list rows when child added or removed in Firebase
                salesList.clear()

                 for (ds in dataSnapshot.children) {
                     //Log.d(TAG, ds.toString())

                     val sale = ds.getValue(Sale::class.java)

                     // Extract object ID key from Fire base and assign to arrayList
                     sale?.objectId = ds.key

                    salesList.add(sale!!)
                     Log.d(TAG, sale.toString())

                 }
                // specify an adapter
                var adapter = SaleAdapter(salesList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

        /************************** End of RecyclerView Adapter *************************/

        // Start Sales activity
        fabSales.setOnClickListener {
            val intent = Intent(activity, SalesActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    companion object {
        fun newInstance(): SalesFragment = SalesFragment()
    }
}



