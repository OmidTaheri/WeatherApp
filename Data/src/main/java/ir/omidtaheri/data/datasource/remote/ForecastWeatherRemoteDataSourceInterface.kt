package ir.omidtaheri.data.datasource.remote

import io.reactivex.Single
import ir.omidtaheri.data.entity.forecastEntity.forecastWeatherDataEntity

interface ForecastWeatherRemoteDataSourceInterface {


    fun ForecastWeatherByName(
        name: String,
        units: String?,
        cnt: Int?
    ): Single<forecastWeatherDataEntity>


    fun ForecastWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String?,
        cnt: Int?
    ): Single<forecastWeatherDataEntity>


}
