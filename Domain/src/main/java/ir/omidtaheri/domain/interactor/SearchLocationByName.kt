package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.searchLoacation.SearchLocationDomainEntity
import ir.omidtaheri.domain.gateway.LocationGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import javax.inject.Inject

class SearchLocationByName @Inject constructor(
    schedulers: Schedulers,
    private val locationRepository: LocationGateWay
) :
    SingleUseCase<String, DataState<SearchLocationDomainEntity>>(schedulers) {

    override fun buildSingle(params: String): Single<DataState<SearchLocationDomainEntity>> {
        return locationRepository.searchLocationByName(params)
    }


}
