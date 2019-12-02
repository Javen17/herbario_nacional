package com.example.herbario_nacional.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.herbario_nacional.adapters.PlantAdapter
import com.example.herbario_nacional.models.Plant

import com.example.herbario_nacional.R
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.ui.viewModels.PlantViewModel
import com.example.herbario_nacional.util.GridItemDecoration
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.mikepenz.iconics.Iconics.applicationContext
import kotlinx.android.synthetic.main.fragment_plants.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlantsFragment : Fragment() {
    private val imageLoader: ImageLoader by inject()
    private lateinit var skeleton: Skeleton
    private val plantAdapter: PlantAdapter by lazy { PlantAdapter(imageLoader) }
    private val plantViewModel: PlantViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_plants, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()

        plantViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    println("Familia: ${result[1].family.name}")
                    println(result)
                    plantAdapter.submitList(result)
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    println("Error: ${error}")
                }
            }
        })

        //plantAdapter.submitList(plants)
        //setupSkeleton()
    }

    private fun setupRecycler(){
        rv_plants.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(GridItemDecoration(4, 2))
            adapter = plantAdapter
        }
    }

    private fun setupSkeleton(){
        skeleton = rv_plants.applySkeleton(R.layout.plant_card, 6)
        skeleton.showShimmer = true
        skeleton.shimmerDurationInMillis = 900
        skeleton.maskCornerRadius = 0f
    }
}