package com.onionmonster.politicalpreparednessv2.network

import android.util.Log
import com.onionmonster.politicalpreparednessv2.Constants
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val TAG = "Dev/ElectionApiService"

private val moshi = Moshi.Builder()
    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
//    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface ElectionApiService {
    @GET("civicinfo/v2/elections")
    suspend fun getProperties(@Query("key") apiKey: String = Constants.API_KEY): ElectionQueryProperty
}

object ElectionsApi {
    val retrofitService: ElectionApiService by lazy {
        retrofit.create(ElectionApiService::class.java)
    }
}

suspend fun getElectionTitles():
        ElectionQueryProperty? {
    return try {
        var electionQueryProperty = ElectionsApi.retrofitService.getProperties()

        Log.d(TAG, "API response: " + electionQueryProperty.elections.toString())

        electionQueryProperty

    } catch (e: Exception) {
        Log.d(TAG, "Failure: ${e.message}")
        null
    }
}