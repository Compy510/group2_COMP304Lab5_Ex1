package com.arshad.group2_comp304lab5_ex1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.arshad.group2_comp304lab5_ex1.databinding.ActivityMaps2Binding


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMaps2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaps2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val landmarkName = intent.getStringExtra("landmarkName")
        if (landmarkName != null) {
            // Use the landmarkName to show the selected landmark on the map.
            // For simplicity, you can create a map of landmark names to their corresponding coordinates
            // and then use the coordinates to add a marker and move the camera to that location.

            val landmarkCoordinates = mapOf(
                "Casa Loma, 1 Austin Terrace, Toronto, ON" to LatLng(43.678301, -79.409415),
                "Osgoode Hall, 130 Queen St W, Toronto, ON" to LatLng(43.651991, -79.386648),
                "Massey Hall, 178 Victoria St, Toronto, ON" to LatLng(43.654684, -79.379066),
                "Aga Khan Museum, 77 Wynford Dr, North York, ON" to LatLng(43.725383, -79.332873),
                "Royal Ontario Museum, 100 Queens Park, Toronto, ON" to LatLng(43.667712, -79.394777),
                "Art Gallery of Ontario, 317 Dundas St W, Toronto, ON" to LatLng(43.653681, -79.392737),
                "Ripley's Aquarium, 288 Bremner Blvd, Toronto, ON" to LatLng(43.642403, -79.385994),
                "Toronto Zoo, 2000 Meadowvale Rd, Toronto, ON" to LatLng(43.820650, -79.181817),
                "CN Tower, 290 Bremner Blvd, Toronto, ON" to LatLng(43.642570, -79.387089)
            )

            val selectedLandmarkLocation = landmarkCoordinates[landmarkName]
            if (selectedLandmarkLocation != null) {
                map.addMarker(MarkerOptions().position(selectedLandmarkLocation).title(landmarkName))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLandmarkLocation, 15f))
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> map.animateCamera(CameraUpdateFactory.zoomIn())
            KeyEvent.KEYCODE_DPAD_DOWN -> map.animateCamera(CameraUpdateFactory.zoomOut())

        }
        return super.onKeyDown(keyCode, event)
    }

}