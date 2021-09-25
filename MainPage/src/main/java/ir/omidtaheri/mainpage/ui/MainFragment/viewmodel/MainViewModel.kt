package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import ir.omidtaheri.androidbase.BaseAndroidViewModel
import ir.omidtaheri.androidbase.Utils.TimeUtils
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetCurrentByCoordinates
import ir.omidtaheri.domain.interactor.GetForecastByCoordinates
import ir.omidtaheri.domain.interactor.SearchLocationByName
import ir.omidtaheri.domain.interactor.base.Schedulers
import ir.omidtaheri.domain.interactor.usecaseParam.GetCurrentByCoordinatesParams
import ir.omidtaheri.domain.interactor.usecaseParam.GetForecastByCoordinatesParams
import ir.omidtaheri.mainpage.entity.LocationEntity.LocationUiEntity
import ir.omidtaheri.mainpage.entity.currentEntity.CurrentWeatherUiEntity
import ir.omidtaheri.mainpage.entity.forecastEntity.ForecastList
import ir.omidtaheri.mainpage.entity.forecastEntity.ForecastWeatherUiEntity
import ir.omidtaheri.mainpage.mapper.CurrentWeatherEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.ForecastWeatherEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.LocationEntityUiDomainMapper
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val schedulers: Schedulers,
    private val getCurrentWeather: GetCurrentByCoordinates,
    private val getForecast: GetForecastByCoordinates,
    private val currentWeatherEntityUiDomainMapper: CurrentWeatherEntityUiDomainMapper,
    private val forecastWeatherEntityUiDomainMapper: ForecastWeatherEntityUiDomainMapper,
    private val searchLocationByName: SearchLocationByName,
    private val locationEntityUiDomainMapper: LocationEntityUiDomainMapper,
    private val state: SavedStateHandle,
    private val mApplication: Application
) :
    BaseAndroidViewModel(mApplication, state) {

    val searchSubject: PublishSubject<String> = PublishSubject.create()


    private val _currentWeatherLiveData: MutableLiveData<CurrentWeatherUiEntity> = MutableLiveData()
    val currentWeatherLiveData: LiveData<CurrentWeatherUiEntity>
        get() = _currentWeatherLiveData

    private val _forecastWeatherLiveData: MutableLiveData<ForecastWeatherUiEntity> =
        MutableLiveData()
    val forecastWeatherLiveData: LiveData<ForecastWeatherUiEntity>
        get() = _forecastWeatherLiveData


    private val _locationUiResultsLiveData: MutableLiveData<List<LocationUiEntity>> =
        MutableLiveData()
    val locationUiResultsLiveData: LiveData<List<LocationUiEntity>>
        get() = _locationUiResultsLiveData


    private val _errorLocationResultsLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLocationResultsLiveData: LiveData<String>
        get() = _errorLocationResultsLiveData


    private val _dayOfWeekLiveData: MutableLiveData<Int> = MutableLiveData()
    val dayOfWeekLiveData: LiveData<Int>
        get() = _dayOfWeekLiveData


    fun getCurrentWeather(lat: Double, log: Double) {
        val params = GetCurrentByCoordinatesParams(lat, log, "metric")

        val disposable = getCurrentWeather.execute(params).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {

                    _currentWeatherLiveData.value =
                        currentWeatherEntityUiDomainMapper.mapToUiEntity(response.data!!)

                }

                is DataState.ERROR -> {
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                            }

                            is UiComponentType.TOAST -> {
                                handleToastError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.DIALOG -> {
                            }
                        }
                    }
                }
            }
        }

        addDisposable(disposable)
    }


    fun getForecastWeather(lat: Double, log: Double) {
        val params = GetForecastByCoordinatesParams(lat, log, "metric", 40)

        val disposable = getForecast.execute(params).subscribeBy { response ->
            when (response) {
                is DataState.SUCCESS -> {

                    _forecastWeatherLiveData.value =
                        forecastWeatherEntityUiDomainMapper.mapToUiEntity(response.data!!)

                }

                is DataState.ERROR -> {
                    response.let { errorDataState ->
                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                                handleSnackBarError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.TOAST -> {
                                handleToastError(errorDataState as DataState.ERROR<Any>)
                            }

                            is UiComponentType.DIALOG -> {
                            }
                        }
                    }
                }
            }
        }

        addDisposable(disposable)
    }


    private fun handleSnackBarError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {

                is MessageHolder.MESSAGE -> {
                    _errorSnackBar.value = messageHolder.message
                }

                is MessageHolder.Res -> _errorSnackBar.value =
                    mApplication.applicationContext.getString(
                        messageHolder.resId
                    )
            }
        }
    }

    private fun handleToastError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _errorToast.value =
                    messageHolder.message
                is MessageHolder.Res -> _errorToast.value =
                    mApplication.applicationContext.getString(
                        messageHolder.resId
                    )
            }
        }
    }

    fun getCurrentTimeInTimeZone(timezone: Int): String {
        val loacalTime = TimeUtils.getCurrentDataTime()
        val timeZone = TimeUtils.getTimeZoneFromOffsetSeconds(timezone)
        val MainTime = TimeUtils.setTimeZoneToDateTime(loacalTime, timeZone)
        _dayOfWeekLiveData.value = MainTime.dayOfWeek
        return TimeUtils.dateTimeFormatter(MainTime, TimeUtils.getCommonPattern(4))
    }


    fun filterForecastItems(categorizeForecastItems: HashMap<Int, ArrayList<ForecastList>>): List<ForecastList> {

        return categorizeForecastItems.filterValues {
            !it.isEmpty()
        }.map {
            it.value[0]
        }.sortedBy {
            it.dt
        }

    }


    fun categorizeForecastItems(
        inputList: ForecastWeatherUiEntity,
        mainTimeZone: Int?,
        currentDay: Int?
    ): HashMap<Int, ArrayList<ForecastList>> {
        val mapOfForecastDays: HashMap<Int, ArrayList<ForecastList>> = hashMapOf()

        inputList.list.sortedBy {
            it.dt
        }.filter {

            val dayOfWeek = getDayOfTimeStamp(mainTimeZone!!, it.dt)
            currentDay?.let {
                (dayOfWeek != currentDay)
            } ?: true

        }.forEach {
            val dayOfWeek = getDayOfTimeStamp(mainTimeZone!!, it.dt)

            if (mapOfForecastDays.keys.contains(dayOfWeek)) {
                mapOfForecastDays.getValue(dayOfWeek).add(it)
            } else {
                val dayList = arrayListOf<ForecastList>()
                dayList.add(it)
                mapOfForecastDays.put(dayOfWeek, dayList)
            }


        }

        return mapOfForecastDays
    }


    fun getDayOfTimeStamp(timezone: Int, dt: Int): Int {
        val timeStampMilis: Long = (dt.toLong() * 1000)
        val loacalTime = TimeUtils.getDataTimeByMillis(timeStampMilis)
        val timeZone = TimeUtils.getTimeZoneFromOffsetSeconds(timezone)
        val mainTime = TimeUtils.setTimeZoneToDateTime(loacalTime, timeZone)
        return mainTime.dayOfWeek
    }


    fun showWeatherIcon(iconName: String, mainId: Int): String {

        val result = when (iconName) {
            "01n" -> "n01"
            "01d" -> "d01"
            "02n" -> "n02"
            "02d" -> "d02"
            "03n" -> "n03"
            "03d" -> "d03"
            "04n" -> "n04"
            "04d" -> "d04"
            "09n" -> "n09"
            "09d" -> "d09"
            "10n" -> "n10"
            "10d" -> "d10"
            "11n" -> if (mainId == 210 || mainId == 211) {
                "n11"
            } else {
                "n55"
            }
            "11d" -> if (mainId == 210 || mainId == 211) {
                "d11"
            } else {
                "d55"
            }
            "13d" -> "d13"
            "13n" -> "n13"
            "50d" -> "d50"
            "50n" -> "n50"
            else -> "n01"
        }

        return result

    }


    fun setBackgroundImage(mainTitle: String, iconName: String): String {

        val result = when (mainTitle) {
            "Thunderstorm" -> {
                if (iconName.contains("n")) "sb" else "rb"
            }
            "Drizzle"
            -> {
                if (iconName.contains("n")) "sba" else "rba"
            }
            "Rain"
            -> {
                if (iconName.contains("n")) "sba" else "rba"
            }
            "Snow"
            -> {
                if (iconName.contains("n")) "sb" else "rb"
            }
            "Atmosphere"
            -> {
                if (iconName.contains("n")) "sm" else "rm"
            }
            "Clear"
            -> {
                if (iconName.contains("n")) "ss" else "rs"
            }
            "Clouds"
            -> {
                if (iconName.contains("n")) "sa" else "ra"
            }

            else -> {
                if (iconName.contains("n")) "sm" else "rm"
            }
        }


        return result
    }


    fun setSearchSubjectObserver() {


        val disposable = searchSubject.debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(schedulers.subscribeOn)
            .filter {
                !it.isEmpty()
            }
            .switchMapSingle {
                _isLoading.postValue(true)

                searchLocationByName.execute(it)
            }
            .observeOn(schedulers.observeOn)
            .subscribeBy { response ->
                when (response) {

                    is DataState.SUCCESS -> {
                        _locationUiResultsLiveData.value =
                            locationEntityUiDomainMapper.mapToUiEntity(response.data!!)
                    }

                    is DataState.ERROR -> {
                        response.let { errorDataState ->

                            when (errorDataState.stateMessage?.uiComponentType) {
                                is UiComponentType.SNACKBAR -> {
                                    handleSnackBarError(errorDataState as DataState.ERROR<Any>)
                                }

                                is UiComponentType.TOAST -> {
                                    handleToastError(errorDataState as DataState.ERROR<Any>)
                                }

                                is UiComponentType.DIALOG -> {
                                    _errorLocationResultsLiveData.value = "Place not found"
                                }
                            }
                        }
                    }
                }

            }

        addDisposable(disposable)

    }


    fun saveLocation(context: Context, lat: Double, log: Double) {
        val prefrence = context.getSharedPreferences("LocationSetting", MODE_PRIVATE)
        val prefrenceEditor = prefrence.edit()
        prefrenceEditor.putString("LocationLat", lat.toString())
        prefrenceEditor.putString("LocationLog", log.toString())
        prefrenceEditor.apply()
    }

    fun getLocation(context: Context): LocationUiEntity {
        val prefrence = context.getSharedPreferences("LocationSetting", MODE_PRIVATE)
        val LocationLat = prefrence.getString("LocationLat", "40.712776")
        val LocationLog = prefrence.getString("LocationLog", " -74.005974")
        return LocationUiEntity(LocationLat?.toDouble()!!, LocationLog?.toDouble()!!, "", "")
    }

    fun saveLocationName(context: Context, cityName: String) {
        val prefrence = context.getSharedPreferences("LocationSetting", MODE_PRIVATE)
        val prefrenceEditor = prefrence.edit()
        prefrenceEditor.putString("cityName", cityName)
        prefrenceEditor.apply()
    }


    fun getLocationName(context: Context): String? {
        val prefrence = context.getSharedPreferences("LocationSetting", MODE_PRIVATE)
        val LocationName = prefrence.getString("cityName", "")
        return LocationName
    }
}
