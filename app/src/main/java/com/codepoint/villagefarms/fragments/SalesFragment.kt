package com.codepoint.villagefarms.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codepoint.villagefarms.R
import com.codepoint.villagefarms.SalesActivity
import kotlinx.android.synthetic.main.fragment_sales.*

class SalesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_sales, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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



