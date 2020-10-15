package ir.omidtaheri.domain.gateway

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.currentEntity.currentWeatherDomainEntity

interface CurrentWeatherGateWay {

    fun currentWeatherByName(
        city_name: String,
        units: String?
    ): Single<DataState<currentWeatherDomainEntity>>

    fun currentWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String?
    ): Single<DataState<currentWeatherDomainEntity>>

}
