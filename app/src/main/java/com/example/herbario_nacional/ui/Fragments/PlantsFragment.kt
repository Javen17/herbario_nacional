package com.example.herbario_nacional.ui.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.herbario_nacional.adapters.PlantAdapter
import com.example.herbario_nacional.models.Plant

import com.example.herbario_nacional.R
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.util.GridItemDecoration
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.android.synthetic.main.fragment_plants.*
import org.koin.android.ext.android.inject

class PlantsFragment : Fragment() {
    private val imageLoader: ImageLoader by inject()
    private lateinit var skeleton: Skeleton
    private val plantAdapter: PlantAdapter by lazy { PlantAdapter(imageLoader) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_plants, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plants = ArrayList<Plant>()
        plants.add(Plant("Girasol", R.drawable.plant1))
        plants.add(Plant("Trompeta China Trepadora", R.drawable.plant2))
        plants.add(Plant("Galio Blanco", R.drawable.plant3))
        plants.add(Plant("Galio Rojo", R.drawable.plant3))
        plants.add(Plant("Galio Blanco", R.drawable.plant3))
        plants.add(Plant("Galio Azul", R.drawable.plant3))
        plants.add(Plant("Galio Blanco", R.drawable.plant3))
        plants.add(Plant("Galio Azul", R.drawable.plant3))
        setupRecycler()
        plantAdapter.submitList(plants)
        //setupSkeleton()
    }

    private fun setupRecycler(){
        rv_plants.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
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
