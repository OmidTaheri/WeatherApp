package ir.omidtaheri.daggercore.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.data.repository.CurrentWeatherRepository
import ir.omidtaheri.data.repository.ForecastWeatherRepository
import ir.omidtaheri.data.repository.LocationRepository
import ir.omidtaheri.domain.gateway.CurrentWeatherGateWay
import ir.omidtaheri.domain.gateway.ForecastWeatherGateWay
import ir.omidtaheri.domain.gateway.LocationGateWay
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun provideCurrentWeatherRepository(
        repository: CurrentWeatherRepository
    ): CurrentWeatherGateWay


    @Singleton
    @Binds
    fun provideForecastWeatherRepository(
        repository: ForecastWeatherRepository
    ): ForecastWeatherGateWay


    @Singleton
    @Binds
    fun provideLocationRepository(
        repository: LocationRepository
    ): LocationGateWay

}
