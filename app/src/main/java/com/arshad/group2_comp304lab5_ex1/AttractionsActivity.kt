package com.arshad.group2_comp304lab5_ex1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AttractionsActivity : AppCompatActivity()
{
    //variables
    private val attractions = mutableListOf("Ripley's Aquarium, 288 Bremner Blvd, Toronto, ON",
        "Toronto Zoo, 2000 Meadowvale Rd, Toronto, ON",
        "CN Tower, 290 Bremner Blvd, Toronto, ON" )
    private lateinit var landmarks: LandmarksAdapter
    private lateinit var landmarksInformation: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attractions)

        landmarks = LandmarksAdapter(attractions)

        landmarksInformation = findViewById(R.id.landmarksInfoAttractions)
        landmarksInformation.layoutManager = LinearLayoutManager(this)
        landmarksInformation.adapter = landmarks

        landmarks.onLandmarkClickListener { x ->
            //open map for the landmark
            //opens main activity, just a test to make sure button works
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}