package ir.omidtaheri.mainpage.entity.forecastEntity


data class ForecastWeatherUiEntity(
    val city: City,
    val list: List<ForecastList>
)