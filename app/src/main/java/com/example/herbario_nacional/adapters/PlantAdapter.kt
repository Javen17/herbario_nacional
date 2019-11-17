package com.example.herbario_nacional.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.herbario_nacional.models.Plant
import com.example.herbario_nacional.R
import com.example.herbario_nacional.imageloader.ImageLoader
import ni.abril.azb.megaboicotapp.ui.view_holders.PlantViewHolder

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