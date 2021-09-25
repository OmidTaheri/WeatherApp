package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.domain.interactor.GetCurrentByCoordinates
import ir.omidtaheri.domain.interactor.GetForecastByCoordinates
import ir.omidtaheri.domain.interactor.SearchLocationByName
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.mainpage.mapper.CurrentWeatherEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.ForecastWeatherEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.LocationEntityUiDomainMapper
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val schedulers: Schedulers,
    private val getCurrentWeather: GetCurrentByCoordinates,
    private val getForecast: GetForecastByCoordinates,
    private val currentWeatherEntityUiDomainMapper: CurrentWeatherEntityUiDomainMapper,
    private val forecastWeatherEntityUiDomainMapper: ForecastWeatherEntityUiDomainMapper,
    private val searchLocationByName: SearchLocationByName,
    private val locationEntityUiDomainMapper: LocationEntityUiDomainMapper,
    private val application: Application
) : ViewModelAssistedFactory<MainViewModel> {
    override fun create(handle: SavedStateHandle): MainViewModel {
        return MainViewModel(
            schedulers,
            getCurrentWeather,
            getForecast,
            currentWeatherEntityUiDomainMapper,
            forecastWeatherEntityUiDomainMapper,
            searchLocationByName,
            locationEntityUiDomainMapper,
            handle,
            application
        )
    }
}
