package com.onionmonster.politicalpreparednessv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DatabaseElection::class, DatabaseRepresentative::class], version = 1, exportSchema = false)
abstract class ElectionsDatabase : RoomDatabase() {
    abstract val electionDao: ElectionDao
    abstract val repDao: RepDao
}

private lateinit var INSTANCE: ElectionsDatabase

fun getDatabase(context: Context): ElectionsDatabase {
    synchronized(ElectionsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ElectionsDatabase::class.java,
                "politics").build()
        }
    }
    return INSTANCE
}
