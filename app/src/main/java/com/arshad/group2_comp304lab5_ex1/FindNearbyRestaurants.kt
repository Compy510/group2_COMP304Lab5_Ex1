package com.arshad.group2_comp304lab5_ex1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient

class FindNearbyRestaurants(private val context: Context, private val apiKey: String) {

    private var placesClient: PlacesClient

    init {
        // Initialize the Places API client
        Places.initialize(context, apiKey)
        placesClient = Places.createClient(context)
    }

    fun getNearbyPlaces(userLocation: LatLng, radius: Int, onComplete: (List<Place>) -> Unit) {
        val placeFields = listOf(Place.Field.NAME, Place.Field.LAT_LNG)

        // Create a FindCurrentPlaceRequest to get the nearby places
        val request = FindCurrentPlaceRequest.newInstance(placeFields)

        // Check for location permission
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("nearby", "Location permission not granted")
            // If permission not granted, handle it here or request permission from the user
            onComplete(emptyList())
            return
        }

        // Get the list of nearby places
        placesClient.findCurrentPlace(request).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val response: FindCurrentPlaceResponse? = task.result
                val nearbyPlaces = mutableListOf<Place>()
                for (placeLikelihood in response?.placeLikelihoods ?: emptyList()) {
                    val place = placeLikelihood.place

                    if (place != null) {

                        val distanceToPlace = calculateDistance(userLocation, place.latLng)
                        if (distanceToPlace <= radius) {
                            nearbyPlaces.add(place)

                        }
                    }
                }
                onComplete(nearbyPlaces)
            } else {
                task.exception?.printStackTrace()
                Log.d("nearby", "Error finding nearby places: ${task.exception?.message}")
                // Handle the error case here
                onComplete(emptyList())
            }
        }
    }

    private fun calculateDistance(location1: LatLng, location2: LatLng): Float {
        val results = FloatArray(1)

        android.location.Location.distanceBetween(
            location1.latitude, location1.longitude,
            location2.latitude, location2.longitude, results
        )

        return results[0] / 1000
    }
}
