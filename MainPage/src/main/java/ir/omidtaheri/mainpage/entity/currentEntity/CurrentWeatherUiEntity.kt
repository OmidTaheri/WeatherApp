package ir.omidtaheri.mainpage.entity.currentEntity


data class currentWeatherUiEntity(
    val coord: Coord,
    val main: Main,
    val name: String,
    val weather: List<Weather>,
    val wind: Wind,
    val timezone: Int
)