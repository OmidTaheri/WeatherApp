package ir.omidtaheri.remote.entity.response.forecastWeather

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)