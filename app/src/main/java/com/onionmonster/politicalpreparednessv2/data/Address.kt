package com.onionmonster.politicalpreparednessv2.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String
): Parcelable