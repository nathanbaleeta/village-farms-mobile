package com.codepoint.villagefarms.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codepoint.villagefarms.R
import com.codepoint.villagefarms.RegisterActivity
import kotlinx.android.synthetic.main.fragment_farmer.*

class FarmerFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_farmer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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



