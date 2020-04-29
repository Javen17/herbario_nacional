package com.example.herbario_nacional.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.herbario_nacional.R
import com.example.herbario_nacional.adapters.PlantAdapter
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.ui.viewModels.SearchViewModel
import com.example.herbario_nacional.util.GridItemDecoration
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private val imageLoader: ImageLoader by inject()

    private val plantAdapter: PlantAdapter by lazy { PlantAdapter(imageLoader) }

    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBy
        setupRecycler()
        searchBy.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, searchViewModel.categoryList)
        searchViewModel.uiState.observe(viewLifecycleOwner, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    plantAdapter.submitList(result)
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    println("Error: $error")
                }
            }
        })

        mi_search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(s:String):Boolean {

                searchViewModel.searchPlantByName(s)

                Toast.makeText(context, "Keep a gold chain in my neck", Toast.LENGTH_SHORT).show()



                return false
            }

            override fun onQueryTextChange(s:String):Boolean {
                return false
            }
        })
    }

    private fun setupRecycler(){
        search_recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(GridItemDecoration(4, 2))
            adapter = plantAdapter
        }
    }
}