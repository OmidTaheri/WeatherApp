package ir.omidtaheri.remote.datasource

import io.reactivex.Single
import ir.omidtaheri.data.datasource.remote.SearchLocationRemoteDataSourceInterface
import ir.omidtaheri.data.entity.searchLoacation.searchLocationDataEntity
import ir.omidtaheri.remote.mapper.SearchLocationResponseToDataEntityMapper
import ir.omidtaheri.remote.service.MapboxApi
import javax.inject.Inject

class SearchLocationRemoteDataSourceImp @Inject constructor(
    private val mapboxApi: MapboxApi,
    private val searchLocationResponseDataoMapper: SearchLocationResponseToDataEntityMapper
) :
    SearchLocationRemoteDataSourceInterface {
    override fun searchLocationByName(city_name: String): Single<searchLocationDataEntity> {
        return mapboxApi.SearchLocationByName(city_name).map {
            searchLocationResponseDataoMapper.mapFromDTO(it)
        }
    }

}
