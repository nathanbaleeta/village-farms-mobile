package com.codepoint.villagefarms.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codepoint.villagefarms.R



class FarmerProfileFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_farmer_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //requireActivity().getOnBackPressedDispatcher().addCallback(this)





    }


    companion object {
        fun newInstance(): FarmerProfileFragment = FarmerProfileFragment()
    }
}