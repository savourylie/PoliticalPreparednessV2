package com.onionmonster.politicalpreparednessv2.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ElectionDao {
    @Query("SELECT * FROM elections")
    fun getElections(): LiveData<List<DatabaseElection>>

    @Query("SELECT * FROM elections WHERE saved = 1")
    fun getSavedElections(): LiveData<List<DatabaseElection>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg elections: DatabaseElection)

    @Query("SELECT * FROM elections WHERE id=:electionId")
//    fun getElectionById(electionId: String): LiveData<List<DatabaseElection>>
    fun getElectionById(electionId: String): LiveData<DatabaseElection>

    @Query("UPDATE elections SET saved=:saved WHERE id=:electionId")
    fun updateSaveStatus(electionId: String, saved: Int)
}