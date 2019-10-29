package com.example.herbario_nacional.ui.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.herbario_nacional.Adapters.PlantAdapter
import com.example.herbario_nacional.Models.Plant

import com.example.herbario_nacional.R

/**
 * A simple [Fragment] subclass.
 */
class PlantsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_plants, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.plant_specimen_list)

        recyclerView.layoutManager= LinearLayoutManager(activity, RecyclerView.VERTICAL,false)

        val plants = ArrayList<Plant>()

        plants.add(
            Plant(
                "Girasol",
                R.drawable.plant1
            )
        )
        plants.add(
            Plant(
                "Trompeta China Trepadora",
                R.drawable.plant2
            )
        )
        plants.add(
            Plant(
                "Galio Blanco",
                R.drawable.plant3
            )
        )

        val adapter = PlantAdapter(plants)

        recyclerView.adapter = adapter

        return view
    }


}
