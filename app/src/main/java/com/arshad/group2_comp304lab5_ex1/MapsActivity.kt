package com.arshad.group2_comp304lab5_ex1

import android.Manifest
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.arshad.group2_comp304lab5_ex1.databinding.ActivityMaps2Binding
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.libraries.places.api.Places
import java.lang.Math.round
import kotlin.math.pow

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMaps2Binding

    private val SEARCHRADIUS : Int = 2500

    val apiKey = "YOUR_API_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaps2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        Places.initialize(applicationContext, apiKey)

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
                addCircle(selectedLandmarkLocation, SEARCHRADIUS.toDouble())
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLandmarkLocation, 15f))

            }
            val nearbyPlacesAPI = FindNearbyRestaurants(this, apiKey)
            val userLocation =
                selectedLandmarkLocation?.let { LatLng(it.latitude, selectedLandmarkLocation.longitude) } // San Francisco

            if (isLocationServicesEnabled(this)) {
                if (userLocation != null) {
                    nearbyPlacesAPI.getNearbyPlaces(userLocation, SEARCHRADIUS) { nearbyPlaces ->
                        showPopup(nearbyPlaces)
                    }
                }
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                this.startActivity(intent)
            }
        }

    }

    private fun isLocationServicesEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    private fun showPopup(places: List<com.google.android.libraries.places.api.model.Place>) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)

        // Access the TextView inside the popup layout
        val popupTextView: TextView = dialog.findViewById(R.id.popup)
        var tempText : String = ""
        var temp = removeDuplicatePlaces(places)

        for(place in temp){
            tempText += "${place.name}, ${place.latLng}"
            Log.d("Testing", "${place.name}, ${place.latLng}")
        }
        popupTextView.text = tempText
        dialog.show()
    }
    // Function to remove duplicate places based on their ID
    fun removeDuplicatePlaces(places: List<com.google.android.libraries.places.api.model.Place>): MutableList<com.google.android.libraries.places.api.model.Place> {
        val uniquePlaces = mutableListOf<com.google.android.libraries.places.api.model.Place>()
        val uniqueLatLng = mutableSetOf<LatLng>()

        for (place in places) {
            val latLng = roundLatLng(place.latLng, 2)
            if (latLng != null && !uniqueLatLng.contains(latLng)) {
                uniqueLatLng.add(latLng)
                uniquePlaces.add(place)
            }
        }
        return uniquePlaces
    }
    fun roundLatLng(latLng: LatLng, decimalPlaces: Int): LatLng {
        val lat = round(latLng.latitude * 10.0.pow(decimalPlaces)) / 10.0.pow(decimalPlaces)
        val lng = round(latLng.longitude * 10.0.pow(decimalPlaces)) / 10.0.pow(decimalPlaces)
        return LatLng(lat, lng)
    }
    private fun addCircle(latLng: LatLng, radius: Double){
        var circleOptions : CircleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(radius)
        circleOptions.strokeColor(Color.argb(255,255,0,0))
        circleOptions.fillColor(Color.argb(64,255,0,0))
        circleOptions.strokeWidth(4F)
        map.addCircle(circleOptions)
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