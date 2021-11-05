package com.onionmonster.politicalpreparednessv2.upcomingscreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.onionmonster.politicalpreparednessv2.data.Election
import com.onionmonster.politicalpreparednessv2.data.ElectionDetails
import com.onionmonster.politicalpreparednessv2.database.getDatabase
import com.onionmonster.politicalpreparednessv2.network.asDomainModel
import com.onionmonster.politicalpreparednessv2.network.getElectionDetails
import com.onionmonster.politicalpreparednessv2.repository.ElectionRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select


class UpcomingDetailsViewModel(election: Election, application: Application) : AndroidViewModel(application) {

    val TAG = "Dev/" + javaClass.simpleName

    val resources = getApplication<Application>().resources

    private val database = getDatabase(application)
    val electionRepository = ElectionRepository(database)

    private val _selectedElection = MutableLiveData<Election>()
    val selectedElection: LiveData<Election>
        get() = _selectedElection

    private val _electionDetails = MutableLiveData<ElectionDetails>()
    val electionDetails: LiveData<ElectionDetails>
        get() = _electionDetails

    private val _electionSaveStatus = MutableLiveData<Int>()
    val electionSaveStatus: LiveData<Int>
        get() = _electionSaveStatus

    init {
        _selectedElection.value = election

        Log.d(TAG, "Election: " + _selectedElection.value.toString())

        viewModelScope.launch {
            _electionSaveStatus.value = electionRepository.getElectionSaveStatus(election)
            val electionDetailsQueryProperty = getElectionDetails(election.id)
            _electionDetails.value = electionDetailsQueryProperty?.asDomainModel()
        }
    }

    fun setElectionSaveStatus(election: Election, SaveStatus: Int) {
        _electionSaveStatus.value = SaveStatus

        viewModelScope.launch {
            election.let {
                electionRepository.setElectionSaveStatus(
                    it, SaveStatus)
            }
        }
    }


}