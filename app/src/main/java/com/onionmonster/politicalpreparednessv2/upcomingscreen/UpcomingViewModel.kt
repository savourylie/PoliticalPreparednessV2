package com.onionmonster.politicalpreparednessv2.upcomingscreen

import android.app.Application
import androidx.lifecycle.*
import com.onionmonster.politicalpreparednessv2.data.Election
import com.onionmonster.politicalpreparednessv2.database.getDatabase
import com.onionmonster.politicalpreparednessv2.network.ElectionProperty
import com.onionmonster.politicalpreparednessv2.repository.ElectionRepository
import kotlinx.coroutines.launch


enum class ElectionApiStatus {LOADING, ERROR, DONE}


class UpcomingViewModel(application: Application) : AndroidViewModel(application), OnElectionSelectedListener, OnSaveIconSelectedListener {
    val TAG = "Dev/" + javaClass.simpleName

    private val database = getDatabase(application)
    private val electionRepository = ElectionRepository(database)

    private val _status = MutableLiveData<ElectionApiStatus>()
    val status: LiveData<ElectionApiStatus>
        get() = _status

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    private val _properties = MutableLiveData<List<ElectionProperty>>()
    val properties: LiveData<List<ElectionProperty>>
        get() = _properties

    init {
        viewModelScope.launch {
            electionRepository.refreshElections()
        }
    }

    val electionList = electionRepository.elections

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

    }
}