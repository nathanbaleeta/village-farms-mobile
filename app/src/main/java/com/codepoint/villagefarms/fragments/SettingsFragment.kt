package com.codepoint.villagefarms.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codepoint.villagefarms.DistrictListActivity
import com.codepoint.villagefarms.MarketListActivity
import com.codepoint.villagefarms.PricePerKgListActivity

import kotlinx.android.synthetic.main.content_settings.*

class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

                return inflater.inflate(
            com.codepoint.villagefarms.R.layout.fragment_settings,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // Triggered event listeners for items on settings activity
        districts.setOnClickListener {
            val intent = Intent(activity, DistrictListActivity::class.java)
            activity?.startActivity(intent)
        }

        pricePerKg.setOnClickListener {
            val intent = Intent(activity, PricePerKgListActivity::class.java)
            activity?.startActivity(intent)
        }

        market.setOnClickListener {
            val intent = Intent(activity, MarketListActivity::class.java)
            activity?.startActivity(intent)
        }
    }




    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}



