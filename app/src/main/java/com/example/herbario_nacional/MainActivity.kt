package com.example.herbario_nacional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView:RecyclerView = findViewById(R.id.plant_specimen_list)

        recyclerView.layoutManager=LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        val plants = ArrayList<Plant>()

        plants.add(Plant("Girasol", R.drawable.plant1))
        plants.add(Plant("Trompeta China Trepadora", R.drawable.plant2))
        plants.add(Plant("Galio Blanco", R.drawable.plant3))

        val adapter = PlantAdapter(plants)

        recyclerView.adapter = adapter
    }
}
