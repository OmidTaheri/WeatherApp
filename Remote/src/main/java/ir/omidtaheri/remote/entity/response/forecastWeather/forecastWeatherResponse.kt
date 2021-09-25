package ir.omidtaheri.remote.entity.response.forecastWeather

data class ForecastWeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastList>,
    val message: Double
)