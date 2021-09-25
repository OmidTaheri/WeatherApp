package ir.omidtaheri.domain.entity.forecastEntity

data class ForecastWeatherDomainEntity(
    val city: City,
    val list: List<ForecastList>
)