package ir.omidtaheri.data.repository

import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.ForecastWeatherRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.ForecastEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.forecastEntity.forecastWeatherDomainEntity
import ir.omidtaheri.domain.gateway.ForecastWeatherGateWay
import javax.inject.Inject

class ForecastWeatherRepository @Inject constructor(
    private val forecastWeatherRemoteDataSource: ForecastWeatherRemoteDataSourceInterface,
    private val forecastEntityDomainDataMapper: ForecastEntityDomainDataMapper

) : ForecastWeatherGateWay {

    override fun ForecastWeatherByName(
        name: String,
        units: String?,
        cnt: Int?
    ): Single<DataState<forecastWeatherDomainEntity>> {


        return forecastWeatherRemoteDataSource.forecastWeatherByName(
            name,
            units,
            cnt
        ).map<DataState<forecastWeatherDomainEntity>> {

            DataState.SUCCESS(
                forecastEntityDomainDataMapper.mapFromDataEntity(it),
                StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
            )
        }.onErrorReturn {
            DataState.ERROR(
                StateMessage(
                    MessageHolder.MESSAGE(it.message ?: "Error"),
                    UiComponentType.SNACKBAR,
                    MessageType.ERROR
                )
            )
        }

    }

    override fun ForecastWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String?,
        cnt: Int?
    ): Single<DataState<forecastWeatherDomainEntity>> {

        return forecastWeatherRemoteDataSource.forecastWeatherByCoordinates(
            lat,
            lon,
            units,
            cnt
        ).map<DataState<forecastWeatherDomainEntity>> {

            DataState.SUCCESS(
                forecastEntityDomainDataMapper.mapFromDataEntity(it),
                StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
            )
        }.onErrorReturn {
            DataState.ERROR(
                StateMessage(
                    MessageHolder.MESSAGE(it.message ?: "Error"),
                    UiComponentType.SNACKBAR,
                    MessageType.ERROR
                )
            )
        }

    }


}
