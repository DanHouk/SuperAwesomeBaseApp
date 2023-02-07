package com.loves.superawesomeapp.models

import java.time.LocalTime

data class SunriseModel(
    val sunrise: LocalTime,
    val sunset: LocalTime,
    val day_length: String,
    val solar_noon: LocalTime
)