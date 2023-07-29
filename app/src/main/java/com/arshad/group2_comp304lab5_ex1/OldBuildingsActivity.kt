package com.arshad.group2_comp304lab5_ex1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OldBuildingsActivity : AppCompatActivity()
{
    //variables
    private val oldBuildings = mutableListOf("Casa Loma, 1 Austin Terrace, Toronto, ON",
        "Osgoode Hall, 130 Queen St W, Toronto, ON", "Massey Hall, 178 Victoria St, Toronto, ON" )
    private lateinit var landmarks: LandmarksAdapter
    private lateinit var landmarksInformation: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old_buildings)

        landmarks = LandmarksAdapter(oldBuildings)

        landmarksInformation = findViewById(R.id.landmarksInfoBuildings)
        landmarksInformation.layoutManager = LinearLayoutManager(this)
        landmarksInformation.adapter = landmarks

        landmarks.onLandmarkClickListener { x ->
            //open map for the landmark
            //opens main activity, just a test to make sure button works
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("landmarkName", x)
            startActivity(intent)
        }

    }

}


