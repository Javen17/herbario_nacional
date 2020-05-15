package com.example.herbario_nacional.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.herbario_nacional.R
import com.example.herbario_nacional.adapters.PlantAdapter
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.ui.viewModels.PlantViewModel
import com.example.herbario_nacional.util.GridItemDecoration
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.fragment_plants.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlantsFragment : Fragment() {
    private val imageLoader: ImageLoader by inject()
    private val plantAdapter: PlantAdapter by lazy { PlantAdapter(imageLoader) }
    private val plantViewModel: PlantViewModel by viewModel()
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var shimmerLayout: ShimmerFrameLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_plants, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shimmerLayout = view.findViewById(R.id.shimmer_container)
        shimmerLayout.startShimmer()

        swipeRefresh = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)

        getData()
        setupRecycler()

        swipeRefresh.setOnRefreshListener{
            getData()
        }
    }

    private fun getData() {
        plantViewModel.uiState.observe(viewLifecycleOwner, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    plantAdapter.submitList(result)
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                }
            }
            swipeRefresh.isRefreshing = false
        })
    }

    private fun setupRecycler(){
        rv_plants.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(GridItemDecoration(4, 2))
            adapter = plantAdapter
        }
    }
}