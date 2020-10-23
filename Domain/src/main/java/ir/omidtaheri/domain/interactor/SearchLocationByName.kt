package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.currentEntity.currentWeatherDomainEntity
import ir.omidtaheri.domain.entity.searchLoacation.searchLocationDomainEntity
import ir.omidtaheri.domain.gateway.CurrentWeatherGateWay
import ir.omidtaheri.domain.gateway.LocationGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import ir.omidtaheri.domain.interactor.usecaseParam.GetCurrentByNameParams
import javax.inject.Inject

class SearchLocationByName @Inject constructor(
    schedulers: Schedulers,
    val locationRepository: LocationGateWay
) :
    SingleUseCase<String, DataState<searchLocationDomainEntity>>(schedulers) {

    override fun buildSingle(params: String): Single<DataState<searchLocationDomainEntity>> {
        return locationRepository.SearchLocationByName(params)
    }


}
