package ir.omidtaheri.domain.interactor

import io.reactivex.Single
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.entity.forecastEntity.ForecastWeatherDomainEntity
import ir.omidtaheri.domain.gateway.ForecastWeatherGateWay
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.base.SingleUseCase
import ir.omidtaheri.domain.interactor.usecaseParam.GetForecastByCoordinatesParams
import javax.inject.Inject

class GetForecastByCoordinates @Inject constructor(
    schedulers: Schedulers,
    private val forecastWeatherRepository: ForecastWeatherGateWay
) :
    SingleUseCase<GetForecastByCoordinatesParams, DataState<ForecastWeatherDomainEntity>>(schedulers) {

    override fun buildSingle(params: GetForecastByCoordinatesParams): Single<DataState<ForecastWeatherDomainEntity>> {
        return forecastWeatherRepository.forecastWeatherByCoordinates(
            params.lat,
            params.lon,
            params.units,
            params.cnt
        )
    }
}
