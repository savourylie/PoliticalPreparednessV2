package com.onionmonster.politicalpreparednessv2.network

import android.util.Log
import com.onionmonster.politicalpreparednessv2.data.ProfilePic

private val TAG = "Dev/ProfilePicProperty"

data class ProfilePicProperty(
    val data: List<Data>?
)

data class Data(
    val name: String,
    val id: String,
    val username: String,
    val profile_image_url: String
)

fun ProfilePicProperty.asDomainModel(): ProfilePic {
    Log.d(TAG, "ProfilePicProperty: " + this.toString())

    data?.let {
        return if (data.isNotEmpty()) {
            ProfilePic(
                username = data[0].username,
                name = data[0].name,
                profilePicUrl = data[0].profile_image_url
            )
        } else {
            ProfilePic(
                username = "",
                name = "",
                profilePicUrl = ""
            )
        }
    }

    return ProfilePic(
        username = "",
        name = "",
        profilePicUrl = ""
    )
}