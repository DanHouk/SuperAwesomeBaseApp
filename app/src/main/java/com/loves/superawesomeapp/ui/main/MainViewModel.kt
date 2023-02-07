package com.loves.superawesomeapp.ui.main

import androidx.lifecycle.ViewModel
import com.loves.superawesomeapp.facade.NetworkFacade
import com.loves.superawesomeapp.models.SunriseResults
import io.reactivex.Flowable
import javax.inject.Inject

class MainViewModel @Inject constructor(private val networkFacade: NetworkFacade) : ViewModel() {
    fun fetchSunriseAndSunset(lat: Double, lng: Double): Flowable<SunriseResults> =
        networkFacade.retrieveSunriseAndSunset(lat, lng)
}