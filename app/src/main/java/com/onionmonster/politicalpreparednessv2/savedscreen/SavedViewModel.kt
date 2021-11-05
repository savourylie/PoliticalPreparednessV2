package com.onionmonster.politicalpreparednessv2.savedscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.onionmonster.politicalpreparednessv2.data.Election
import com.onionmonster.politicalpreparednessv2.database.getDatabase
import com.onionmonster.politicalpreparednessv2.repository.ElectionRepository
import com.onionmonster.politicalpreparednessv2.upcomingscreen.OnElectionSelectedListener
import com.onionmonster.politicalpreparednessv2.upcomingscreen.OnSaveIconSelectedListener
import kotlinx.coroutines.launch

class SavedViewModel(application: Application):
    AndroidViewModel(application), OnElectionSelectedListener, OnSaveIconSelectedListener {

    val TAG = "Dev/" + javaClass.simpleName

    private val database = getDatabase(application)
    private val electionRepository = ElectionRepository(database)

    init {
        viewModelScope.launch {
            electionRepository.refreshElections()
        }
    }

    val electionList = electionRepository.elections_saved

    private val _navigateToElectionDetail = MutableLiveData<Election>()
    val navigateToElectionDetail: LiveData<Election>
        get() = _navigateToElectionDetail

    fun onElectionDetailNavigated() {
        _navigateToElectionDetail.value = null
    }

    override fun onElectionClicked(election: Election) {
        _navigateToElectionDetail.value = election
    }

    override fun onSaveIconClicked(election: Election) {
        TODO("Not yet implemented")
    }

}