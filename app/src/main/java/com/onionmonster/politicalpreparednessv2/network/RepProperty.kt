package com.onionmonster.politicalpreparednessv2.network

data class RepProperty(
    val contests: List<Contest>
)

data class Contest(
    val roles: List<String>,
    val candidates: List<Candidate>
)

data class Candidate(
    val name: String,
    val party: String,
    val candidateUrl: String,
    val channels: List<Channel>?
)

data class Channel(
    val type: String,
    val id: String
)