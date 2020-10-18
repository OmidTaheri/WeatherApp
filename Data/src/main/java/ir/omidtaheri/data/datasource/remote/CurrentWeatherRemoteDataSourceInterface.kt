package ir.omidtaheri.data.datasource.remote

import io.reactivex.Single
import ir.omidtaheri.data.entity.currentEntity.currentWeatherDataEntity

interface CurrentWeatherRemoteDataSourceInterface {

    fun currentWeatherByName(
        city_name: String,
        units: String?
    ): Single<currentWeatherDataEntity>

    fun currentWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String?
    ): Single<currentWeatherDataEntity>

}
