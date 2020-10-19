package ir.omidtaheri.mainpage.entity.forecastEntity


data class forecastWeatherUiEntity(
    val city: City,
    val list: List<forecastList>
)