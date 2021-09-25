package ir.omidtaheri.data.repository

import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.SearchLocationRemoteDataSourceInterface
import ir.omidtaheri.data.mapper.LocationEntityDomainDataMapper
import ir.omidtaheri.domain.datastate.*
import ir.omidtaheri.domain.entity.searchLoacation.searchLocationDomainEntity
import ir.omidtaheri.domain.gateway.LocationGateWay
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val searchLocationRemoteDataSourceInterface: SearchLocationRemoteDataSourceInterface,
    private val locationEntityDomainDataMapper: LocationEntityDomainDataMapper
) : LocationGateWay {

    override fun SearchLocationByName(city_name: String): Single<DataState<searchLocationDomainEntity>> {

        return searchLocationRemoteDataSourceInterface.searchLocationByName(
            city_name
        ).map<DataState<searchLocationDomainEntity>> {

            DataState.SUCCESS(
                locationEntityDomainDataMapper.mapFromDataEntity(it),
                StateMessage(MessageHolder.NONE, UiComponentType.NONE, MessageType.NONE)
            )
        }.onErrorReturn {
            DataState.ERROR(
                StateMessage(
                    MessageHolder.MESSAGE(it.message ?: "Error"),
                    UiComponentType.DIALOG,
                    MessageType.ERROR
                )
            )
        }


    }


}
