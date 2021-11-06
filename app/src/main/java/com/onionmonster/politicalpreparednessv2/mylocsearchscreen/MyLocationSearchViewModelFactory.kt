package com.onionmonster.politicalpreparednessv2.mylocsearchscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onionmonster.politicalpreparednessv2.data.Address

class MyLocationSearchViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyLocationSearchViewModel::class.java)) {
            return MyLocationSearchViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
