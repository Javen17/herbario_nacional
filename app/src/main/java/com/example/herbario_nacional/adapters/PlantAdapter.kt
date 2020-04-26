package com.example.herbario_nacional.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.herbario_nacional.models.Plant
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.viewHolder.PlantViewHolder

class PlantAdapter(var imageLoader: ImageLoader) : ListAdapter<Plant, PlantViewHolder>(DIFF_CALLBACK){
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Plant>() {
            override fun areItemsTheSame(oldItem: Plant, newItem: Plant) = oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: Plant, newItem: Plant) = oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlantViewHolder.create(parent)
    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) = holder.bind(getItem(position), imageLoader)
}