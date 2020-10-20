package ir.omidtaheri.mainpage.ui.MainFragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.androidbase.Utils.TimeUtils
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetCurrentByCoordinates
import ir.omidtaheri.domain.interactor.GetForecastByCoordinates
import ir.omidtaheri.domain.interactor.usecaseParam.GetCurrentByCoordinatesParams
import ir.omidtaheri.domain.interactor.usecaseParam.GetForecastByCoordinatesParams
import ir.omidtaheri.mainpage.entity.currentEntity.currentWeatherUiEntity
import ir.omidtaheri.mainpage.entity.forecastEntity.forecastList
import ir.omidtaheri.mainpage.entity.forecastEntity.forecastWeatherUiEntity
import ir.omidtaheri.mainpage.mapper.CurrentWeatherEntityUiDomainMapper
import ir.omidtaheri.mainpage.mapper.ForecastWeatherEntityUiDomainMapper

class MainViewModel(
    val getCurrentWeather: GetCurrentByCoordinates,
    val getForecast: GetForecastByCoordinates,
    val currentWeatherEntityUiDomainMapper: CurrentWeatherEntityUiDomainMapper,
    val forecastWeatherEntityUiDomainMapper: ForecastWeatherEntityUiDomainMapper,
    application: Application
) :
    BaseViewModel(application) {


    private val _currentWeatherLiveData: MutableLiveData<currentWeatherUiEntity>
    val currentWeatherLiveData: LiveData<currentWeatherUiEntity>
        get() = _currentWeatherLiveData

    private val _forecastWeatherLiveData: MutableLiveData<forecastWeatherUiEntity>
    val forecastWeatherLiveData: LiveData<forecastWeatherUiEntity>
        get() = _forecastWeatherLiveData


    private val _dayOfWeekLiveData: MutableLiveData<Int>
    val dayOfWeekLiveData: LiveData<Int>
        get() = _dayOfWeekLiveData


    init {
        _currentWeatherLiveData = MutableLiveData()
        _forecastWeatherLiveData = MutableLiveData()
        _dayOfWeekLiveData = MutableLiveData()
    }

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
                    _ErrorSnackBar.value = messageHolder.message
                }

                is MessageHolder.Res -> _ErrorSnackBar.value =
                    ApplicationClass.getString(
                        messageHolder.resId
                    )
            }
        }
    }

    private fun handleToastError(errorDataState: DataState.ERROR<Any>) {
        errorDataState.stateMessage!!.message.let { messageHolder ->

            when (messageHolder) {
                is MessageHolder.MESSAGE -> _ErrorToast.value =
                    messageHolder.message
                is MessageHolder.Res -> _ErrorToast.value =
                    ApplicationClass.getString(
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


    fun filterForecastItems(categorizeForecastItems: HashMap<Int, ArrayList<forecastList>>): List<forecastList> {

        return categorizeForecastItems.filterValues {
            !it.isEmpty()
        }.map {
            it.value[0]
        }.sortedBy {
            it.dt
        }

    }


    fun categorizeForecastItems(
        inputList: forecastWeatherUiEntity,
        mainTimeZone: Int?,
        currentDay: Int?
    ): HashMap<Int, ArrayList<forecastList>> {
        val mapOfForecastDays: HashMap<Int, ArrayList<forecastList>> = hashMapOf()

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
                val dayList = arrayListOf<forecastList>()
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
        val MainTime = TimeUtils.setTimeZoneToDateTime(loacalTime, timeZone)
        return MainTime.dayOfWeek
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

}
