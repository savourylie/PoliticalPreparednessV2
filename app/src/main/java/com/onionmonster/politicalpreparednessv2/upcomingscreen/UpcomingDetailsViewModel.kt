package com.onionmonster.politicalpreparednessv2.upcomingscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onionmonster.politicalpreparednessv2.data.Election


class UpcomingDetailsViewModel(election: Election, app: Application) : AndroidViewModel(app) {
    val resources = getApplication<Application>().resources

    private val _selectedElection = MutableLiveData<Election>()
    val selectedElection: LiveData<Election>
        get() = _selectedElection

    init {
        _selectedElection.value = election
    }

}