package ir.omidtaheri.data.repository

import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.CurrentWeatherRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.CurrentEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.currentEntity.currentWeatherDomainEntity
import ir.omidtaheri.domain.gateway.CurrentWeatherGateWay
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(
    val currentWeatherRemoteDataSource: CurrentWeatherRemoteDataSourceInterface,
    val currentEntityMapper: CurrentEntityDomainDataMapper
) : CurrentWeatherGateWay {


    override fun currentWeatherByName(
        city_name: String,
        units: String?
    ): Single<DataState<currentWeatherDomainEntity>> {

        return currentWeatherRemoteDataSource.currentWeatherByName(
            city_name,
            units
        ).map<DataState<currentWeatherDomainEntity>> {

            DataState.SUCCESS(
                currentEntityMapper.mapFromDataEntity(it),
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

    override fun currentWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String?
    ): Single<DataState<currentWeatherDomainEntity>> {


        return currentWeatherRemoteDataSource.currentWeatherByCoordinates(
            lat,
            lon,
            units
        ).map<DataState<currentWeatherDomainEntity>> {

            DataState.SUCCESS(
                currentEntityMapper.mapFromDataEntity(it),
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
