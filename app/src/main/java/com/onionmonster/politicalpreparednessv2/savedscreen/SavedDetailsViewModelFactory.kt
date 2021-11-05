package com.onionmonster.politicalpreparednessv2.savedscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onionmonster.politicalpreparednessv2.data.Election

class SavedDetailsViewModelFactory(
    private val election: Election,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedDetailsViewModel::class.java)) {
            return SavedDetailsViewModel(election, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
