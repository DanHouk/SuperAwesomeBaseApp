package com.loves.superawesomeapp.facade

import com.loves.superawesomeapp.models.SunriseResults
import com.loves.superawesomeapp.network.NetworkService
import io.reactivex.Flowable

class NetworkFacade(private val networkService: NetworkService) {
    fun retrieveSunriseAndSunset(lat: Double, lng: Double): Flowable<SunriseResults> =
        networkService.getSunriseAndSunset(lat, lng)
}