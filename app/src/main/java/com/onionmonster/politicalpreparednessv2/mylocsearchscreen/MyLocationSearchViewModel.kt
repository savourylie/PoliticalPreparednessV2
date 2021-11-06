package com.onionmonster.politicalpreparednessv2.mylocsearchscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.onionmonster.politicalpreparednessv2.data.Address
import com.onionmonster.politicalpreparednessv2.database.getDatabase
import com.onionmonster.politicalpreparednessv2.repository.ElectionRepository
import kotlinx.coroutines.launch

class MyLocationSearchViewModel(application: Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repRepository = ElectionRepository(database)


//    init {
//        viewModelScope.launch {
//            repRepository.refreshReps(address)
//        }
//    }
//
//    val repList = repRepository.reps
}