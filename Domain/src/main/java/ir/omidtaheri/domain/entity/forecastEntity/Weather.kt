package ir.omidtaheri.domain.entity.forecastEntity

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)