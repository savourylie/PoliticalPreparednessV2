package com.onionmonster.politicalpreparednessv2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ElectionDao {
    @Query("SELECT * FROM elections")
    fun getElections(): LiveData<List<DatabaseElection>>

//    @Query("SELECT * FROM elections WHERE saved = 1")
//    suspend fun getSavedElection(): LiveData<List<DatabaseElection>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg elections: DatabaseElection)


}