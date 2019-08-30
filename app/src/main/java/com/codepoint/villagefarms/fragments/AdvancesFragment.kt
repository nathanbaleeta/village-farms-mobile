package com.codepoint.villagefarms.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codepoint.villagefarms.R

class AdvancesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_advances, container, false)
    }


    companion object {
        fun newInstance(): AdvancesFragment = AdvancesFragment()
    }
}



