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

    private var position: Int = 0
    private var query: String = String()
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_search, container, false)

        viewPager = view.findViewById(R.id.searchPager)
        tabs = view.findViewById(R.id.search_sheet_tabs)

        searchPagerAdapter = SearchAdapter(childFragmentManager)
        viewPager.adapter = searchPagerAdapter
        tabs.setupWithViewPager(viewPager)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get current tab
        tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                val pass: Unit = Unit
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val pass: Unit = Unit
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                position = tab.position
            }
        })

        mi_search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(s:String):Boolean {

                if(position == 0) {
                    searchViewModel.searchPlantByName(s)
                } else {
                    searchViewModel.searchFungusByName(s)
                }

                Toast.makeText(context, getString(R.string.searching), Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(s:String):Boolean {
                query = s
                return true
            }
        })

          // button listeners
        btn_common_name.setOnClickListener {
            if(position == 0) {
                searchViewModel.searchPlantByName(query)
            } else {
                searchViewModel.searchFungusByName(query)
            }
        }

        btn_location.setOnClickListener {
            if(position == 0) {
                searchViewModel.searchPlantByLocation(query)
            } else {
                searchViewModel.searchFungusByLocation(query)
            }
        }

        btn_recollection.setOnClickListener {
            if(position == 0) {
                searchViewModel.searchPlantByRecollectionArea(query)
            } else {
                searchViewModel.searchFungusByRecollectionArea(query)
            }
        }

    }


}