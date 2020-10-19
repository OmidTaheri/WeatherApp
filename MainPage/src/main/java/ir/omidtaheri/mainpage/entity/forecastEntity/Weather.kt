package ir.omidtaheri.mainpage.entity.forecastEntity

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)