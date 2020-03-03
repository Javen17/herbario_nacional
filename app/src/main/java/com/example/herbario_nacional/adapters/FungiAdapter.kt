package com.example.herbario_nacional.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import com.example.herbario_nacional.viewHolder.FungusViewHolder

class FungiAdapter(var imageLoader: ImageLoader) : ListAdapter<FunghiSpecimen, FungusViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FunghiSpecimen>() {
            override fun areItemsTheSame(oldItem: FunghiSpecimen, newItem: FunghiSpecimen) = oldItem.species.common_name == newItem.species.common_name
            override fun areContentsTheSame(oldItem: FunghiSpecimen, newItem: FunghiSpecimen) = oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FungusViewHolder.create(parent)
    override fun onBindViewHolder(holder: FungusViewHolder, position: Int) = holder.bind(getItem(position), imageLoader)

}