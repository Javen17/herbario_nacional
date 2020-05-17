package com.example.herbario_nacional.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.herbario_nacional.R
import com.example.herbario_nacional.adapters.FungiAdapter
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.ui.viewModels.SearchViewModel
import com.example.herbario_nacional.util.GridItemDecoration
import kotlinx.android.synthetic.main.fragment_fungi.*
import kotlinx.android.synthetic.main.fragment_search_funghi.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFunghiFragment : Fragment() {

    private val imageLoader: ImageLoader by inject()
    private val fungiAdapter: FungiAdapter by lazy{ FungiAdapter(imageLoader)}
    private val searchViewModel: SearchViewModel by viewModel()

    private var query: String = String()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_funghi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()

        searchViewModel.funghiState.observe(viewLifecycleOwner, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    fungiAdapter.submitList(result)
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                }
            }
        })

        funghi_search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(s:String):Boolean {
                searchViewModel.searchFungusByName(s)
                Toast.makeText(context, getString(R.string.searching), Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(s:String):Boolean {
                query = s
                return true
            }
        })

        btn_common_name.setOnClickListener {
            searchViewModel.searchFungusByName(query)
            Toast.makeText(context, getString(R.string.searching), Toast.LENGTH_SHORT).show()
        }

        btn_family.setOnClickListener {
            searchViewModel.searchFungusByFamily(query)
            Toast.makeText(context, getString(R.string.searching), Toast.LENGTH_SHORT).show()
        }

        btn_genus.setOnClickListener {
            searchViewModel.searchFungusByGenus(query)
            Toast.makeText(context, getString(R.string.searching), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecycler(){
        searchFunghi_recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(GridItemDecoration(4, 2))
            adapter = fungiAdapter
        }
    }
}
