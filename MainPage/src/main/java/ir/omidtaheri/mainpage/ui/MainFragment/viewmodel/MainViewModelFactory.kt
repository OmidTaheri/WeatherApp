package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetCurrentByCoordinates
import ir.omidtaheri.domain.interactor.GetForecastByCoordinates
import ir.omidtaheri.domain.interactor.SearchLocationByName
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.mainpage.mapper.CurrentWeatherEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.ForecastWeatherEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.LocationEntityUiDomainMapper
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    val schedulers: Schedulers,
    val getCurrentWeather: GetCurrentByCoordinates,
    val getForecast: GetForecastByCoordinates,
    val currentWeatherEntityUiDomainMapper: CurrentWeatherEntityUiDomainMapper,
    val forecastWeatherEntityUiDomainMapper: ForecastWeatherEntityUiDomainMapper,
    val searchLocationByName: SearchLocationByName,
    val locationEntityUiDomainMapper: LocationEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            schedulers,
            getCurrentWeather,
            getForecast,
            currentWeatherEntityUiDomainMapper,
            forecastWeatherEntityUiDomainMapper, searchLocationByName, locationEntityUiDomainMapper,
            application
        ) as T
    }
}
