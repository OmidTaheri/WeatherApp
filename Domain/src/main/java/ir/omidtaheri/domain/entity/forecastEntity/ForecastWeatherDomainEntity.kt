package ir.omidtaheri.domain.entity.forecastEntity

data class forecastWeatherDomainEntity(
    val city: City,
    val list: List<forecastList>
)