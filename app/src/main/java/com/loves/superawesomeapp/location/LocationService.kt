package com.loves.superawesomeapp.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

class LocationService(private val context: Context) {
    private var fusedLocationProvider: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

        }
    }

    private fun checkLocationSettings() {
        val locationSettingsRequest =
            LocationSettingsRequest.Builder().addLocationRequest(buildLocationRequest())
        val settingsClient: SettingsClient = LocationServices.getSettingsClient(context)
        settingsClient.checkLocationSettings(locationSettingsRequest.build())
            .addOnCompleteListener { result ->
                try {
                    result.getResult(ApiException::class.java)
                } catch (exception: ApiException) {
                    Log.e("Location Service", exception.localizedMessage ?: "N/A")
                }
            }.addOnFailureListener { throwable ->
                if (throwable is ApiException)
                    when (throwable.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {

                            try {
                                val resolvable = ResolvableApiException(throwable.status)
                                throw resolvable
                            } catch (e: ClassCastException) {
                                Log.e("Location Service", e.localizedMessage ?: "N/A")
                            }
                        }
                    }
            }
    }

    private fun buildLocationRequest(): LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 500)
            .setMinUpdateIntervalMillis(500)
            .build()

    @SuppressLint("MissingPermission")
    private fun getLocationAvailability() {
        fusedLocationProvider.locationAvailability
            .addOnSuccessListener { startLocationUpdates() }
            .addOnFailureListener { throwable ->
                Log.e("LocationService", throwable.localizedMessage ?: "N/A")
            }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProvider.requestLocationUpdates(
            buildLocationRequest(),
            locationCallback,
            Looper.getMainLooper()
        )
            .addOnFailureListener { throwable ->
                Log.e("LocationService", throwable.localizedMessage ?: "N/A")
            }
    }
}