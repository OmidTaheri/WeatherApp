package ir.omidtaheri.domain.entity.forecastEntity


data class ForecastList(

    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)