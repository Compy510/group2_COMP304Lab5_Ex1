package com.arshad.group2_comp304lab5_ex1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity()
{

    //variables
    private val typesOfLandmarks = mutableListOf("Old Buildings", "Museums", "Attractions")
    private lateinit var landmarkTypes: LandmarkTypesAdapter
    private lateinit var landmarkClassification: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        landmarkTypes = LandmarkTypesAdapter(typesOfLandmarks)

        landmarkClassification = findViewById(R.id.typesOfLandmarks)
        landmarkClassification.layoutManager = LinearLayoutManager(this)
        landmarkClassification.adapter = landmarkTypes

        landmarkTypes.onLandmarkTypeClickListener { x ->
            selectLandmarkType(x)
        }
    }

    private fun selectLandmarkType(x: String)
    {
        val selectedType = when (x)
        {
            "Old Buildings" -> Intent(this, OldBuildingsActivity::class.java)
            "Museums" -> Intent(this, MuseumsActivity::class.java)
            "Attractions" -> Intent(this, AttractionsActivity::class.java)
            else -> return
        }
        startActivity(selectedType)
    }
}
