package com.example.herbario_nacional.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.herbario_nacional.R
import com.example.herbario_nacional.adapters.SearchAdapter
import com.example.herbario_nacional.ui.viewModels.SearchViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout
    private lateinit var searchPagerAdapter: SearchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_search, container, false)

        viewPager = view.findViewById(R.id.searchPager)
        tabs = view.findViewById(R.id.search_sheet_tabs)

        searchPagerAdapter = SearchAdapter(childFragmentManager)
        viewPager.adapter = searchPagerAdapter
        tabs.setupWithViewPager(viewPager)

        return view
    }
}