package com.onionmonster.politicalpreparednessv2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Representative(
    val role: String,
    val name: String,
    val party: String,
    val webSiteUrl: String,
    val facebookUrl: String,
    val twitterUrl: String
    ): Parcelable {
}