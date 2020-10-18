package ir.omidtaheri.remote.entity.response.currentWeather

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)