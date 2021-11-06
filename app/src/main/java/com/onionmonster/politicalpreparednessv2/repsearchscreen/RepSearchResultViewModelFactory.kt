package com.onionmonster.politicalpreparednessv2.repsearchscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onionmonster.politicalpreparednessv2.data.Address

class RepSearchResultViewModelFactory(
    private val address: Address,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepSearchResultViewModel::class.java)) {
            return RepSearchResultViewModel(address, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
