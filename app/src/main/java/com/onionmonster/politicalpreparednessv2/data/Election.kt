package com.onionmonster.politicalpreparednessv2.data

import java.util.*

data class Election(val id: String, val title: String, val datetime: String, val saved: Int)

data class ElectionDetails(val id: String,
                           val title: String,
                           val electionDay: String,
                           val votingLocation: String,
                           val ballotInfo: String
)