package com.example.herbario_nacional.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.herbario_nacional.Models.Plant
import com.example.herbario_nacional.R

class PlantAdapter(var list:ArrayList<Plant>): RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vw = LayoutInflater.from(parent.context).inflate(R.layout.plant_item, parent, false)

        return ViewHolder(vw)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: Plant) {
            val title: TextView = itemView.findViewById(R.id.commonName)
            val thumbnail: ImageView = itemView.findViewById(R.id.plant_image)

            title.text = data.name
            Glide.with(itemView.context).load(data.image).into(thumbnail)

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "${data.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}