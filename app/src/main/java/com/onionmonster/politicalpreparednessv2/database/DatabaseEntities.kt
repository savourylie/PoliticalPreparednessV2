package com.onionmonster.politicalpreparednessv2.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.onionmonster.politicalpreparednessv2.data.Election
import com.onionmonster.politicalpreparednessv2.data.Representative

@Entity(tableName = "elections")
data class DatabaseElection constructor(
    @PrimaryKey
    val id: String,
    val title: String,
    val datetime: String,
    val saved: Int
)

fun List<DatabaseElection>.asDomainModel(): List<Election> {
    return map {
        Election(id = it.id, title = it.title, datetime = it.datetime, saved = it.saved)
    }
}


@Entity(tableName = "representatives")
data class DatabaseRepresentative constructor(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val role: String,
    val name: String,
    val party: String,
    val webSiteUrl: String?,
    val facebookUrl: String?,
    val twitterUrl: String?
)

@JvmName("asDomainModelDatabaseRepresentative")
fun List<DatabaseRepresentative>.asDomainModel(): List<Representative> {
    return map {
        Representative(
            role = it.role,
            name = it.name,
            party = it.party,
            webSiteUrl = it.webSiteUrl ?: "",
            facebookUrl = it.facebookUrl ?: "",
            twitterUrl = it.twitterUrl ?: ""
        )
    }
}