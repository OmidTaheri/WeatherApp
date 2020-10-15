package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.forecastEntity.forecastWeatherDomainEntity
import ir.omidtaheri.domain.gateway.ForecastWeatherGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import ir.omidtaheri.domain.interactor.usecaseParam.GetForecastByCoordinatesParams
import javax.inject.Inject

class GetForecastByCoordinates @Inject constructor(
    schedulers: Schedulers,
    val forecastWeatherRepository: ForecastWeatherGateWay
) :
    SingleUseCase<GetForecastByCoordinatesParams, DataState<forecastWeatherDomainEntity>>(schedulers) {

    override fun buildSingle(params: GetForecastByCoordinatesParams): Single<DataState<forecastWeatherDomainEntity>> {
        return forecastWeatherRepository.ForecastWeatherByCoordinates(
            params.lat,
            params.lon,
            params.units,
            params.cnt
        )
    }
}
