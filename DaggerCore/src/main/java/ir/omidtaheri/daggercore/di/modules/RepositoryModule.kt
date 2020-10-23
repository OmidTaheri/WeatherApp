package ir.omidtaheri.daggercore.di.modules

import dagger.Module
import dagger.Provides
import ir.omidtaheri.data.repository.CurrentWeatherRepository
import ir.omidtaheri.data.repository.ForecastWeatherRepository
import ir.omidtaheri.data.repository.LocationRepository
import ir.omidtaheri.domain.gateway.CurrentWeatherGateWay
import ir.omidtaheri.domain.gateway.ForecastWeatherGateWay
import ir.omidtaheri.domain.gateway.LocationGateWay

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


    @Provides
    fun provideLocationRepository(
        repository: LocationRepository
    ): LocationGateWay = repository

}
