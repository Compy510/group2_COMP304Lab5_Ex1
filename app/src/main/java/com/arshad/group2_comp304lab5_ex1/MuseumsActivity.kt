package com.arshad.group2_comp304lab5_ex1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MuseumsActivity : AppCompatActivity()
{
    //variables
    private val museums = mutableListOf("Aga Khan Museum, 77 Wynford Dr, North York, ON",
        "Royal Ontario Museum, 100 Queens Park, Toronto, ON",
        "Art Gallery of Ontario, 317 Dundas St W, Toronto, ON" )
    private lateinit var landmarks: LandmarksAdapter
    private lateinit var landmarksInformation: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museums)

        landmarks = LandmarksAdapter(museums)

        landmarksInformation = findViewById(R.id.landmarksInfoMuseums)
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