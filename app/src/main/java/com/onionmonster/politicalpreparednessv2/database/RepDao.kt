package com.onionmonster.politicalpreparednessv2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepDao {
    @Query("SELECT * FROM representatives")
    fun getReps(): LiveData<List<DatabaseRepresentative>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg reps: DatabaseRepresentative)
}