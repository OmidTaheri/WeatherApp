package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.forecastEntity.ForecastWeatherDomainEntity
import ir.omidtaheri.domain.gateway.ForecastWeatherGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import ir.omidtaheri.domain.interactor.usecaseParam.GetForecastByNameParams
import javax.inject.Inject

class GetForecastByName @Inject constructor(
    schedulers: Schedulers,
    private val forecastWeatherRepository: ForecastWeatherGateWay
) :
    SingleUseCase<GetForecastByNameParams, DataState<ForecastWeatherDomainEntity>>(schedulers) {

    override fun buildSingle(params: GetForecastByNameParams): Single<DataState<ForecastWeatherDomainEntity>> {
        return forecastWeatherRepository.forecastWeatherByName(
            params.name,
            params.units,
            params.cnt
        )
    }
}
