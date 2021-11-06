package com.onionmonster.politicalpreparednessv2.mylocsearchscreen

import android.app.Application
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.onionmonster.politicalpreparednessv2.data.Address
import com.onionmonster.politicalpreparednessv2.database.getDatabase
import com.onionmonster.politicalpreparednessv2.repository.ElectionRepository
import kotlinx.coroutines.launch

class MyLocationSearchViewModel(application: Application): AndroidViewModel(application) {

    val TAG = javaClass.simpleName

    private val database = getDatabase(application)
    val repRepository = ElectionRepository(database)
    val currentAddress = MutableLiveData<String>()

//    init {
//        viewModelScope.launch {
////            repRepository.refreshReps(address)
//        }
//    }

    val repList = repRepository.reps

    fun getCurrentAddress(gCoder: Geocoder, fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            // Got last known location. In some rare situations this can be null.
            location?.apply {
                val addresses: ArrayList<android.location.Address> =
                    ArrayList(gCoder.getFromLocation(this.latitude, this.longitude, 1))

                if (addresses.size > 0) {
                    val address = addresses[0]

                    var addressString = ""

                    for (i in 0..address.maxAddressLineIndex) {
                        addressString += address.getAddressLine(i) + " "
                    }

                    Log.d(TAG, "Location received: " + addressString.trim())

                    currentAddress.value = "Rep for my Address: " + addressString
                }
            }
        }
    }
}