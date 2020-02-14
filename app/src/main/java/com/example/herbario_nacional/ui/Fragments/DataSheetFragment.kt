package com.example.herbario_nacional.ui.Fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.herbario_nacional.adapters.PagerAdapter

import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.Activities.DataSheetInformation
import com.google.android.material.tabs.TabLayout

class DataSheetFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_data_sheet, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        tabs = view.findViewById(R.id.data_sheet_tabs)

        val fragmentAdapter = PagerAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPager)

        return view
    }
}
