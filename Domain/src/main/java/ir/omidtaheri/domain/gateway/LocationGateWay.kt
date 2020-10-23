package ir.omidtaheri.domain.gateway

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.searchLoacation.searchLocationDomainEntity

interface LocationGateWay {

    fun SearchLocationByName(
        city_name: String
    ): Single<DataState<searchLocationDomainEntity>>


}
