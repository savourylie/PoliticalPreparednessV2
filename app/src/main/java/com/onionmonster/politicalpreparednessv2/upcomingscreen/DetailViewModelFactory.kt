package com.onionmonster.politicalpreparednessv2.upcomingscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onionmonster.politicalpreparednessv2.data.Election

class UpcomingDetailsViewModelFactory(
    private val election: Election,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingDetailsViewModel::class.java)) {
            return UpcomingDetailsViewModel(election, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
