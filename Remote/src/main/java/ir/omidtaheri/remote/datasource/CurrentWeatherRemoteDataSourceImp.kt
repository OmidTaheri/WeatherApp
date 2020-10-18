package ir.omidtaheri.remote.datasource

import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.CurrentWeatherRemoteDataSourceInterface
import ir.omidtaheri.data.entity.currentEntity.currentWeatherDataEntity
import ir.omidtaheri.remote.mapper.CurrentWeatherResponseToDataEntityMapper
import ir.omidtaheri.remote.service.WeatherApi
import javax.inject.Inject

class CurrentWeatherRemoteDataSourceImp @Inject constructor(
    val weatherApi: WeatherApi,
    val currentWeatherResponseDtoMapper: CurrentWeatherResponseToDataEntityMapper
) :
    CurrentWeatherRemoteDataSourceInterface {

    override fun currentWeatherByName(
        city_name: String,
        units: String?
    ): Single<currentWeatherDataEntity> {
        return weatherApi.CurrentWeatherByName(city_name, units).map {
            currentWeatherResponseDtoMapper.mapFromDTO(it)
        }
    }

    override fun currentWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String?
    ): Single<currentWeatherDataEntity> {
        return weatherApi.CurrentWeatherByCoordinates(lat, lon, units).map {
            currentWeatherResponseDtoMapper.mapFromDTO(it)
        }
    }


}
