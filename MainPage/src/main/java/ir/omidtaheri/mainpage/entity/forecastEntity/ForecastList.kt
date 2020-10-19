package ir.omidtaheri.mainpage.entity.forecastEntity

data class forecastList(
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

