package ir.omidtaheri.domain.gateway

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.searchLoacation.SearchLocationDomainEntity

interface LocationGateWay {

    fun searchLocationByName(
        city_name: String
    ): Single<DataState<SearchLocationDomainEntity>>


}
