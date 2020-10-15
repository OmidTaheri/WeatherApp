package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.currentEntity.currentWeatherDomainEntity
import ir.omidtaheri.domain.gateway.CurrentWeatherGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import ir.omidtaheri.domain.interactor.usecaseParam.GetCurrentByNameParams
import javax.inject.Inject

class GetCurrentByName @Inject constructor(
    schedulers: Schedulers,
    val currentWeatherRepository: CurrentWeatherGateWay
) :
    SingleUseCase<GetCurrentByNameParams, DataState<currentWeatherDomainEntity>>(schedulers) {

    override fun buildSingle(params: GetCurrentByNameParams): Single<DataState<currentWeatherDomainEntity>> {
        return currentWeatherRepository.currentWeatherByName(
            params.city_name,
            params.units

        )
    }
}
