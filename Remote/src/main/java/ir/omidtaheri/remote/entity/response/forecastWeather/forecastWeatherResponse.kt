package ir.omidtaheri.remote.entity.response.forecastWeather

data class forecastWeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<forecastList>,
    val message: Double
)