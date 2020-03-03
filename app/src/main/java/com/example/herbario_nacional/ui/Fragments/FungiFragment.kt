package com.example.herbario_nacional.ui.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.herbario_nacional.R
import com.example.herbario_nacional.adapters.FungiAdapter
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.ui.viewModels.FungusViewModel
import com.example.herbario_nacional.util.GridItemDecoration
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.android.synthetic.main.fragment_fungi.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class FungiFragment : Fragment() {

    private val imageLoader: ImageLoader by inject()
    private lateinit var skeleton: Skeleton
    private val fungiAdapter: FungiAdapter by lazy{ FungiAdapter(imageLoader)}
    private val fungusViewModel: FungusViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fungi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()

        fungusViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    fungiAdapter.submitList(result)

                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    println("Error: $error")
                }
            }
        })
    }

    private fun setupRecycler(){
        rv_fungus.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(GridItemDecoration(4, 2))
            adapter = fungiAdapter
        }
    }

    private fun setupSkeleton(){
        skeleton = rv_fungus.applySkeleton(R.layout.fungus_card, 6)
        skeleton.showShimmer = true
        skeleton.shimmerDurationInMillis = 900
        skeleton.maskCornerRadius = 0f
    }


}
