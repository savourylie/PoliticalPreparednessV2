package com.onionmonster.politicalpreparednessv2.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Database
import com.onionmonster.politicalpreparednessv2.data.Address
import com.onionmonster.politicalpreparednessv2.database.ElectionsDatabase
import com.onionmonster.politicalpreparednessv2.database.asDomainModel
import com.onionmonster.politicalpreparednessv2.network.getElectionTitles
import com.onionmonster.politicalpreparednessv2.data.Election
import com.onionmonster.politicalpreparednessv2.data.Representative
import com.onionmonster.politicalpreparednessv2.database.DatabaseElection
import com.onionmonster.politicalpreparednessv2.network.RepProperty
import com.onionmonster.politicalpreparednessv2.network.asDatabaseModel
import com.onionmonster.politicalpreparednessv2.network.getContests
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

    val elections_saved: LiveData<List<Election>> = Transformations.map(database.electionDao.getSavedElections()) {
        it.asDomainModel()
    }

    val reps: LiveData<List<Representative>> = Transformations.map(database.repDao.getReps()) {
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

    suspend fun refreshReps(address: Address) {
        withContext(Dispatchers.IO) {
            val repProperty = getContests(address = address)

//            if (repProperty != null) {
//                Log.d(TAG, "Add some logic")
//
////                repProperty.contests
//                }

            }
        }
    }

    suspend fun getElectionSaveStatus(election: Election): Int? {
        val databaseElections = database.electionDao.getElectionById(election.id)

        Log.d(TAG, "electionId: " + election.id)

        return databaseElections?.saved
    }

    suspend fun setElectionSaveStatus(election: Election, saveStatus: Int) {
        withContext(Dispatchers.IO) {
            database.electionDao.updateSaveStatus(election.id, saveStatus)
        }
    }
}