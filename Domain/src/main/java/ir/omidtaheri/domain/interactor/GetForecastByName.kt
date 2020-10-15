package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.forecastEntity.forecastWeatherDomainEntity
import ir.omidtaheri.domain.gateway.ForecastWeatherGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import ir.omidtaheri.domain.interactor.usecaseParam.GetForecastByNameParams
import javax.inject.Inject

class GetForecastByName @Inject constructor(
    schedulers: Schedulers,
    val forecastWeatherRepository: ForecastWeatherGateWay
) :
    SingleUseCase<GetForecastByNameParams, DataState<forecastWeatherDomainEntity>>(schedulers) {

    override fun buildSingle(params: GetForecastByNameParams): Single<DataState<forecastWeatherDomainEntity>> {
        return forecastWeatherRepository.ForecastWeatherByName(
            params.name,
            params.units,
            params.cnt
        )
    }
}
