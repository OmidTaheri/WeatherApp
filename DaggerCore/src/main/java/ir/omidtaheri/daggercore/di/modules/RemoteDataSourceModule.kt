package ir.omidtaheri.daggercore.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.data.datasource.remote.CurrentWeatherRemoteDataSourceInterface
import ir.omidtaheri.data.datasource.remote.ForecastWeatherRemoteDataSourceInterface
import ir.omidtaheri.data.datasource.remote.SearchLocationRemoteDataSourceInterface
import ir.omidtaheri.remote.datasource.CurrentWeatherRemoteDataSourceImp
import ir.omidtaheri.remote.datasource.ForecastWeatherRemoteDataSourceImp
import ir.omidtaheri.remote.datasource.SearchLocationRemoteDataSourceImp
import javax.inject.Singleton

@Module(includes = [RemoteModule::class])
interface RemoteDataSourceModule {

    @Singleton
    @Binds
    fun provideCurrentWeatherRemoteDataSource(
        datasource: CurrentWeatherRemoteDataSourceImp
    ): CurrentWeatherRemoteDataSourceInterface

    @Singleton
    @Binds
    fun provideForecastWeatherRemoteDataSource(
        datasource: ForecastWeatherRemoteDataSourceImp
    ): ForecastWeatherRemoteDataSourceInterface


    @Singleton
    @Binds
    fun provideSearchLocationRemoteDataSource(
        datasource: SearchLocationRemoteDataSourceImp
    ): SearchLocationRemoteDataSourceInterface
}
