package com.onionmonster.politicalpreparednessv2.network

import android.util.Log
import com.onionmonster.politicalpreparednessv2.Constants.TW_BASE_URL
import com.onionmonster.politicalpreparednessv2.Constants.TW_B_TOKEN
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
    .build()

val client: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(OAuthInterceptor("Bearer", TW_B_TOKEN))
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(TW_BASE_URL)
    .client(client)
    .build()

// API interfaces
interface ProfilePicApiService {
    @GET("2/users/by")
    suspend fun getProfileProperties(@Query("usernames") username: String,
                                     @Query("user.fields") user_fields: String = "profile_image_url"
    ): ProfilePicProperty
}

object ProfilePicApi {
    val retrofitService: ProfilePicApiService by lazy {

        retrofit.create(ProfilePicApiService::class.java)
    }
}

class OAuthInterceptor(private val tokenType: String, private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "$tokenType $accessToken").build()

        return chain.proceed(request)
    }
}

suspend fun getProfileObject(twitterAccountId: String):
        ProfilePicProperty? {

    val TAG = "Dev/ProfilePicApiService"

    return try {
        val profilePicProperty = ProfilePicApi.retrofitService.getProfileProperties(username=twitterAccountId)

        profilePicProperty

    } catch (e: Exception) {
        Log.d(TAG, "Failure: ${e.message}")
        null
    }
}