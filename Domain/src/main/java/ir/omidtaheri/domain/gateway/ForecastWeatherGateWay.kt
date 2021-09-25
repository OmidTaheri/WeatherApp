package ir.omidtaheri.domain.gateway

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.forecastEntity.ForecastWeatherDomainEntity

interface ForecastWeatherGateWay {

    fun forecastWeatherByName(
        name: String,
        units: String?,
        cnt: Int?
    ): Single<DataState<ForecastWeatherDomainEntity>>


    fun forecastWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String?,
        cnt: Int?
    ): Single<DataState<ForecastWeatherDomainEntity>>

}
