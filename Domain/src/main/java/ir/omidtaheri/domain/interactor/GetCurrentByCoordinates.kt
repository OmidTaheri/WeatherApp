package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.currentEntity.currentWeatherDomainEntity
import ir.omidtaheri.domain.gateway.CurrentWeatherGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import ir.omidtaheri.domain.interactor.usecaseParam.GetCurrentByCoordinatesParams
import javax.inject.Inject

class GetCurrentByCoordinates @Inject constructor(
    schedulers: Schedulers,
    private val currentWeatherRepository: CurrentWeatherGateWay
) :
    SingleUseCase<GetCurrentByCoordinatesParams, DataState<currentWeatherDomainEntity>>(schedulers) {

    override fun buildSingle(params: GetCurrentByCoordinatesParams): Single<DataState<currentWeatherDomainEntity>> {
        return currentWeatherRepository.currentWeatherByCoordinates(
            params.lat,
            params.lon,
            params.units
        )
    }
}
