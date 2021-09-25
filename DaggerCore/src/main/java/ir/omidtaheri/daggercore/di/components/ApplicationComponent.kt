package ir.omidtaheri.daggercore.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ir.omidtaheri.daggercore.di.modules.ApplicationModule
import ir.omidtaheri.daggercore.di.modules.RemoteDataSourceModule
import ir.omidtaheri.daggercore.di.modules.RepositoryModule
import ir.omidtaheri.domain.gateway.CurrentWeatherGateWay
import ir.omidtaheri.domain.gateway.ForecastWeatherGateWay
import ir.omidtaheri.domain.gateway.LocationGateWay
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RemoteDataSourceModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun inject(applicationClass: Application)
    fun schedulers(): ir.omidtaheri.domain.interactor.base.Schedulers
    fun currentWeatherRepo(): CurrentWeatherGateWay
    fun forecastWeatherRepo(): ForecastWeatherGateWay
    fun locationRepo(): LocationGateWay
    fun application(): Application

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance @Named("mapboxBaseUrl")  mapboxBaseUrl: String,
            @BindsInstance @Named("url") baseUrl: String,
            @BindsInstance @Named("mapBoxApiKey") mapBoxApiKey: String,
            @BindsInstance @Named("apiKey") apiKey: String
        ): ApplicationComponent
    }


}
