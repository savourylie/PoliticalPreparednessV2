package com.onionmonster.politicalpreparednessv2.network

data class ProfilePicProperty(
    val data: List<Data>
)

data class Data(
    val name: String,
    val id: String,
    val username: String,
    val profile_image_url: String
)