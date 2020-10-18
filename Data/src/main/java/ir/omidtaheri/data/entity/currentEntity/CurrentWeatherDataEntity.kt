package ir.omidtaheri.data.entity.currentEntity


data class currentWeatherDataEntity(
    val coord: Coord,
    val main: Main,
    val name: String,
    val weather: List<Weather>,
    val wind: Wind,
    val timezone: Int
)