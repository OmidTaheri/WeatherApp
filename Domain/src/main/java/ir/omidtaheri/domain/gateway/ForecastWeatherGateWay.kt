package ir.omidtaheri.domain.gateway

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.forecastEntity.forecastWeatherDomainEntity

interface ForecastWeatherGateWay {

    fun ForecastWeatherByName(
        name: String,
        units: String?,
        cnt: Int?
    ): Single<DataState<forecastWeatherDomainEntity>>


    fun ForecastWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String?,
        cnt: Int?
    ): Single<DataState<forecastWeatherDomainEntity>>

}
