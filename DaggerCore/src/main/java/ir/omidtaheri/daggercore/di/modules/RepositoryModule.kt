package ir.omidtaheri.daggercore.di.modules

import dagger.Module
import dagger.Provides
import ir.omidtaheri.data.repository.CurrentWeatherRepository
import ir.omidtaheri.data.repository.ForecastWeatherRepository
import ir.omidtaheri.domain.gateway.CurrentWeatherGateWay
import ir.omidtaheri.domain.gateway.ForecastWeatherGateWay

@Module
class RepositoryModule {

    @Provides
    fun provideCurrentWeatherRepository(
        repository: CurrentWeatherRepository
    ): CurrentWeatherGateWay = repository


    @Provides
    fun provideForecastWeatherRepository(
        repository: ForecastWeatherRepository
    ): ForecastWeatherGateWay = repository
}
