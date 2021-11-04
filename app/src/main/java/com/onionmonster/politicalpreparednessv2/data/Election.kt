package com.onionmonster.politicalpreparednessv2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Election(val id: String, val title: String, val datetime: String, val saved: Int): Parcelable

data class ElectionDetails(val id: String,
                           val title: String,
                           val electionDay: String,
                           val votingLocation: String,
                           val ballotInfo: String
)