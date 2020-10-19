package ir.omidtaheri.daggercore.di.modules

import dagger.Module
import dagger.Provides
import ir.omidtaheri.data.datasource.remote.CurrentWeatherRemoteDataSourceInterface
import ir.omidtaheri.data.datasource.remote.ForecastWeatherRemoteDataSourceInterface
import ir.omidtaheri.remote.datasource.CurrentWeatherRemoteDataSourceImp
import ir.omidtaheri.remote.datasource.ForecastWeatherRemoteDataSourceImp

@Module(includes = [RemoteModule::class ])
class RemoteDataSourceModule {

    @Provides
    fun provideCurrentWeatherRemoteDataSource(
        datasource: CurrentWeatherRemoteDataSourceImp
    ): CurrentWeatherRemoteDataSourceInterface = datasource

    @Provides
    fun provideForecastWeatherRemoteDataSource(
        datasource: ForecastWeatherRemoteDataSourceImp
    ): ForecastWeatherRemoteDataSourceInterface = datasource
}
