package ir.omidtaheri.daggercore.di.components

import android.app.Application
import dagger.Component
import ir.omidtaheri.daggercore.di.modules.ApplicationModule
import ir.omidtaheri.daggercore.di.modules.RemoteDataSourceModule
import ir.omidtaheri.daggercore.di.modules.RepositoryModule
import ir.omidtaheri.domain.gateway.CurrentWeatherGateWay
import ir.omidtaheri.domain.gateway.ForecastWeatherGateWay
import ir.omidtaheri.domain.gateway.LocationGateWay

@Component(modules = [ApplicationModule::class, RemoteDataSourceModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun inject(applicationClass: Application)
    fun schedulers(): ir.omidtaheri.domain.interactor.base.Schedulers
    fun currentWeatherRepo(): CurrentWeatherGateWay
    fun forecastWeatherRepo(): ForecastWeatherGateWay
    fun locationRepo(): LocationGateWay
    fun application(): Application
}
