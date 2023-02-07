package com.loves.superawesomeapp.network

import com.loves.superawesomeapp.models.SunriseResults
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

const val JSON_STRING = "json"

interface NetworkService {
    @GET(JSON_STRING)
    fun getSunriseAndSunset(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): Flowable<SunriseResults>
}