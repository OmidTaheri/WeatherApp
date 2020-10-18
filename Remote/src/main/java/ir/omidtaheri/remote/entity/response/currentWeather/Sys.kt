package ir.omidtaheri.remote.entity.response.currentWeather

data class Sys(
    val country: String,
    val id: Int,
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)