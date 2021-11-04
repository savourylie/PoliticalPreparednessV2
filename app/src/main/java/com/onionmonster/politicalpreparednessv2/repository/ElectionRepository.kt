package com.onionmonster.politicalpreparednessv2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.onionmonster.politicalpreparednessv2.database.ElectionsDatabase
import com.onionmonster.politicalpreparednessv2.database.asDomainModel
import com.onionmonster.politicalpreparednessv2.network.getElectionTitles
import com.onionmonster.politicalpreparednessv2.data.Election
import com.onionmonster.politicalpreparednessv2.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching election data from the internet and storing them on disk.
 */
class ElectionRepository(private val database: ElectionsDatabase) {
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
}