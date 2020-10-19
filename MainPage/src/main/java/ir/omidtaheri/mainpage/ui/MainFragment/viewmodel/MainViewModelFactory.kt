package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.omidtaheri.domain.interactor.GetCurrentByCoordinates
import ir.omidtaheri.domain.interactor.GetForecastByCoordinates
import ir.omidtaheri.mainpage.mapper.CurrentWeatherEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.ForecastWeatherEntityUiDomainMapper
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    val getCurrentWeather: GetCurrentByCoordinates,
    val getForecast: GetForecastByCoordinates,
    val currentWeatherEntityUiDomainMapper: CurrentWeatherEntityUiDomainMapper,
    val forecastWeatherEntityUiDomainMapper: ForecastWeatherEntityUiDomainMapper,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            getCurrentWeather,
            getForecast,
            currentWeatherEntityUiDomainMapper,
            forecastWeatherEntityUiDomainMapper,
            application
        ) as T
    }
}
