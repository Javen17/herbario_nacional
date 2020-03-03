package com.example.herbario_nacional.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import com.example.herbario_nacional.viewHolder.FungusViewHolder
import com.example.herbario_nacional.viewHolder.PlantViewHolder

class FungiAdapter(var imageLoader: ImageLoader) : ListAdapter<PlantSpecimen, PlantViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlantSpecimen>() {
            override fun areItemsTheSame(oldItem: PlantSpecimen, newItem: PlantSpecimen) = oldItem.species.common_name == newItem.species.common_name
            override fun areContentsTheSame(oldItem: PlantSpecimen, newItem: PlantSpecimen) = oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlantViewHolder.create(parent)
    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) = holder.bind(getItem(position), imageLoader)

}