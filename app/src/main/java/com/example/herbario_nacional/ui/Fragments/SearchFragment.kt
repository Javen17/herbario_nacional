package com.example.herbario_nacional.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.viewpager.widget.ViewPager

import com.example.herbario_nacional.R
import com.example.herbario_nacional.adapters.PagerAdapter
import com.example.herbario_nacional.ui.viewModels.SearchViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    private val searchViewModel: SearchViewModel by viewModel()
    private var query: String = String()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_data_sheet, container, false)

        viewPager = view.findViewById(R.id.searchPager)
        tabs = view.findViewById(R.id.search_sheet_tabs)

        val fragmentAdapter = PagerAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPager)

        return view

//        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        searchViewModel.uiState.observe(viewLifecycleOwner, Observer {
//            val dataState = it ?: return@Observer
//            if (dataState.result != null && !dataState.result.consumed){
//                dataState.result.consume()?.let { result ->
//                    adapter.adapterDataList += result
//                    adapter.notifyDataSetChanged()
//                }
//            }
//            if (dataState.error != null && !dataState.error.consumed){
//                dataState.error.consume()?.let { error ->
//                    println("Error: $error")
//                }
//            }
//        })

        mi_search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(s:String):Boolean {
                searchViewModel.searchPlantByName(s)
                Toast.makeText(context, getString(R.string.searching), Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(s:String):Boolean {
                query = s
                return true
            }
        })

        // button listeners
//        btn_common_name.setOnClickListener {
//            if() {
//                searchViewModel.searchPlantByName(query)
//            } else {
//                searchViewModel.searchFungusByName(query)
//            }
//        }
//
//        btn_location.setOnClickListener {
//            if() {
//                searchViewModel.searchPlantByLocation(query)
//            } else {
//                searchViewModel.searchFungusByLocation(query)
//            }
//        }
//
//        btn_recollection.setOnClickListener {
//            if() {
//                searchViewModel.searchPlantByRecollectionArea(query)
//            } else {
//                searchViewModel.searchFungusByRecollectionArea(query)
//            }
//        }

    }


}