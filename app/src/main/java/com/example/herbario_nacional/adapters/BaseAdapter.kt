package com.example.herbario_nacional.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.herbario_nacional.R
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import com.example.herbario_nacional.viewHolder.BaseViewHolder
import com.example.herbario_nacional.viewHolder.FungusViewHolder
import com.example.herbario_nacional.viewHolder.PlantViewHolder

class DataAdapter(private val context: Context?, var imageLoader: ImageLoader) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    var adapterDataList: List<Any> = emptyList()

    companion object {
        private const val TYPE_PLANT = 0
        private const val TYPE_FUNGHI = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_PLANT -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.plant_card, parent, false)
                PlantViewHolder(view)
            }
            TYPE_FUNGHI -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.fungus_card, parent, false)
                FungusViewHolder(view)
            }
            else -> throw IllegalArgumentException(context?.getString(R.string.invalid_new_type))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]



        when (holder) {
            is PlantViewHolder -> holder.bind(element as PlantSpecimen, imageLoader)
            is FungusViewHolder -> holder.bind(element as FunghiSpecimen, imageLoader)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = adapterDataList.size
}