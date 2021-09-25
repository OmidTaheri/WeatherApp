package ir.omidtaheri.data.datasource.remote

import io.reactivex.Single
import ir.omidtaheri.data.entity.forecastEntity.forecastWeatherDataEntity

interface ForecastWeatherRemoteDataSourceInterface {


    fun forecastWeatherByName(
        name: String,
        units: String?,
        cnt: Int?
    ): Single<forecastWeatherDataEntity>


    fun forecastWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String?,
        cnt: Int?
    ): Single<forecastWeatherDataEntity>


}
