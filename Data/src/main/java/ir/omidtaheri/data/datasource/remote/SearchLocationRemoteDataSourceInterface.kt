package ir.omidtaheri.data.datasource.remote

import io.reactivex.Single
import ir.omidtaheri.data.entity.searchLoacation.searchLocationDataEntity

interface SearchLocationRemoteDataSourceInterface {


    fun SearchLocationByName(
        city_name: String
    ): Single<searchLocationDataEntity>


}
