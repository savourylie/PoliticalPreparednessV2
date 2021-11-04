package com.onionmonster.politicalpreparednessv2.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Database
import com.onionmonster.politicalpreparednessv2.database.ElectionsDatabase
import com.onionmonster.politicalpreparednessv2.database.asDomainModel
import com.onionmonster.politicalpreparednessv2.network.getElectionTitles
import com.onionmonster.politicalpreparednessv2.data.Election
import com.onionmonster.politicalpreparednessv2.database.DatabaseElection
import com.onionmonster.politicalpreparednessv2.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching election data from the internet and storing them on disk.
 */
class ElectionRepository(private val database: ElectionsDatabase) {
    val TAG = "Dev/" + javaClass.simpleName

    val elections: LiveData<List<Election>> = Transformations.map(database.electionDao.getElections()) {
        it.asDomainModel()
    }

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            val electionQueryProperty = getElectionTitles()

            if (electionQueryProperty != null) {
                database.electionDao.insertAll(*electionQueryProperty.asDatabaseModel())
            }
        }
    }

    fun getElectionSaveStatus(election: Election): Int? {
        val databaseElections = database.electionDao.getElectionById(election.id)
        val allElections = database.electionDao.getElections()

        Log.d(TAG, "electionId: " + election.id)
        Log.d(TAG, "Get all elections: " + allElections.value.toString())
//        Log.d(TAG, databaseElections.value!![0].toString())
//        Log.d(TAG, databaseElections.value!!.toString())

//        return databaseElections.value?.get(0)?.saved
        return databaseElections.value?.saved
    }

    suspend fun setElectionSaveStatus(election: Election, saveStatus: Int) {
        withContext(Dispatchers.IO) {
            database.electionDao.updateSaveStatus(election.id, saveStatus)
        }
    }
}