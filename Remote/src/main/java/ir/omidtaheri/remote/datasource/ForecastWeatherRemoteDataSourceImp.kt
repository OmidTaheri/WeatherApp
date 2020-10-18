package ir.omidtaheri.remote.datasource

import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.ForecastWeatherRemoteDataSourceInterface
import ir.omidtaheri.data.entity.forecastEntity.forecastWeatherDataEntity
import ir.omidtaheri.remote.mapper.ForecastWeatherResponseToDataEntityMapper
import ir.omidtaheri.remote.service.WeatherApi
import javax.inject.Inject

class ForecastWeatherRemoteDataSourceImp @Inject constructor(
    val weatherApi: WeatherApi,
    val forecastWeatherResponseDtoMapper: ForecastWeatherResponseToDataEntityMapper
) :
    ForecastWeatherRemoteDataSourceInterface {

    override fun ForecastWeatherByName(
        name: String,
        units: String?,
        cnt: Int?
    ): Single<forecastWeatherDataEntity> {
        return weatherApi.ForecastWeatherByName(
            name,
            units,
            cnt
        ).map {
            forecastWeatherResponseDtoMapper.mapFromDTO(it)
        }
    }

    override fun ForecastWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String?,
        cnt: Int?
    ): Single<forecastWeatherDataEntity> {
        return weatherApi.ForecastWeatherByCoordinates(
            lat,
            lon,
            units,
            cnt
        ).map {
            forecastWeatherResponseDtoMapper.mapFromDTO(it)
        }
    }
}
