package ir.omidtaheri.domain.entity.currentEntity

data class currentWeatherDomainEntity(
    val coord: Coord,
    val main: Main,
    val name: String,
    val weather: List<Weather>,
    val wind: Wind
)