package com.onionmonster.politicalpreparednessv2.network

import android.util.Log
import com.onionmonster.politicalpreparednessv2.Constants
import com.onionmonster.politicalpreparednessv2.data.Address
import com.onionmonster.politicalpreparednessv2.data.ElectionDetails
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

// API interfaces
interface ElectionApiService {
    @GET("civicinfo/v2/elections")
    suspend fun getProperties(@Query("key") apiKey: String = Constants.API_KEY): ElectionQueryProperty
}

interface ElectionDetailsApiService {
    @GET("civicinfo/v2/voterinfo")
    suspend fun getProperties(@Query("key") apiKey: String = Constants.API_KEY,
                              @Query("address") address: String = Constants.QUERY_ADDRESS,
                              @Query("electionId") electionId: String
    ): ElectionDetailsQueryProperty
}

interface RepContestApiService {
    @GET("civicinfo/v2/voterinfo")
    suspend fun getProperties(@Query("key") apiKey: String = Constants.API_KEY,
                              @Query("electionId") electionId: String = "2000",
                              @Query("address") address: String

    ): RepProperty
}

// API objects
object ElectionsApi {
    val retrofitService: ElectionApiService by lazy {
        retrofit.create(ElectionApiService::class.java)
    }
}

object ElectionsDetailsApi {
    val retrofitService: ElectionDetailsApiService by lazy {
        retrofit.create(ElectionDetailsApiService::class.java)
    }
}

object RepContestApi {
    val retrofitService: RepContestApiService by lazy {
        retrofit.create(RepContestApiService::class.java)
    }
}

// API request functions
suspend fun getElectionTitles():
        ElectionQueryProperty? {

    val TAG = "Dev/ElectionApiService"

    return try {
        var electionQueryProperty = ElectionsApi.retrofitService.getProperties()

        Log.d(TAG, "API response: " + electionQueryProperty.elections.toString())

        electionQueryProperty

    } catch (e: Exception) {
        Log.d(TAG, "Failure: ${e.message}")
        null
    }
}

suspend fun getElectionDetails(electionId: String):
        ElectionDetailsQueryProperty? {

    val TAG = "Dev/ElectionDetailsApiService"

    return try {
        val electionDetailsQueryProperty = ElectionsDetailsApi.retrofitService.getProperties(electionId=electionId)

        Log.d(TAG, "API response: " + electionDetailsQueryProperty.election.name)

        electionDetailsQueryProperty

    } catch (e: Exception) {
        Log.d(TAG, "Failure: ${e.message}")
        null
    }
}

suspend fun getContests(address: Address): RepProperty? {
    val TAG = "Dev/RepContestApiService"

    return try {
        val repProperty = RepContestApi.retrofitService.getProperties(address=address.toString())
        repProperty
    } catch (e: Exception) {
        Log.d(TAG, "Failure: ${e.message}")
        null
    }
}