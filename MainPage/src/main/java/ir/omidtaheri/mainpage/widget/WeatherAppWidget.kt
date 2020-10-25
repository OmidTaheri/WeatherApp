package ir.omidtaheri.mainpage.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import io.reactivex.rxkotlin.subscribeBy
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.UiComponentType
import ir.omidtaheri.domain.interactor.GetCurrentByCoordinates
import ir.omidtaheri.domain.interactor.usecaseParam.GetCurrentByCoordinatesParams
import ir.omidtaheri.mainpage.R
import ir.omidtaheri.mainpage.di.components.DaggerMainComponent
import ir.omidtaheri.mainpage.entity.LocationEntity.LocationUiEntity
import ir.omidtaheri.mainpage.entity.currentEntity.currentWeatherUiEntity
import ir.omidtaheri.mainpage.mapper.CurrentWeatherEntityUiDomainMapper
import ir.omidtaheri.mainpage.widget.utils.AlarmManagerUtils
import ir.omidtaheri.uibase.getImage
import javax.inject.Inject


/**
 * Implementation of App Widget functionality.
 */
class WeatherAppWidget : AppWidgetProvider() {


    @Inject
    lateinit var getCurrentWeather: GetCurrentByCoordinates

    @Inject
    lateinit var currentWeatherEntityUiDomainMapper: CurrentWeatherEntityUiDomainMapper


    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (AlarmManagerUtils.WEATHER_WIDGET_UPDATE.equals(intent.action)) {
            val thisAppWidget = ComponentName(context.packageName, javaClass.name)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val ids = appWidgetManager.getAppWidgetIds(thisAppWidget)
            onUpdate(context, appWidgetManager, ids)

        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        ConfigDaggerComponent(context)
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {

        val currentLocationCoord = getLocation(context)
        getCurrentWeather(
            currentLocationCoord.lat,
            currentLocationCoord.log,
            context,
            appWidgetManager,
            appWidgetId
        )


    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        AlarmManagerUtils.startRepeatedAlarm(context!!, 60000)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        AlarmManagerUtils.cancelRepeatedAlarm(context!!)
    }


    fun ConfigDaggerComponent(context: Context) {
        DaggerMainComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(context.applicationContext!!))
            .build()
            .inject(this)
    }


    fun getCurrentWeather(
        lat: Double,
        log: Double,
        context: Context,
        appWidgetManager: AppWidgetManager, appWidgetId: Int
    ) {

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.weather_app_widget)

        val params = GetCurrentByCoordinatesParams(lat, log, "metric")

        getCurrentWeather.execute(params).subscribeBy { response ->
            when (response) {

                is DataState.SUCCESS -> {


                    val currentWeatherUiEntity =
                        currentWeatherEntityUiDomainMapper.mapToUiEntity(response.data!!)
                    showData(currentWeatherUiEntity, views, appWidgetManager, appWidgetId, context)
                }

                is DataState.ERROR -> {
                    response.let { errorDataState ->

                        when (errorDataState.stateMessage?.uiComponentType) {
                            is UiComponentType.SNACKBAR -> {
                            }

                            is UiComponentType.TOAST -> {
                            }

                            is UiComponentType.DIALOG -> {
                            }
                        }
                    }
                }
            }
        }


    }

    fun showData(
        data: currentWeatherUiEntity,
        remoteViews: RemoteViews,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int, context: Context
    ) {

        remoteViews.setTextViewText(R.id.weatherStatus, data.weather.get(0).main)
        remoteViews.setTextViewText(R.id.cityName, data.name)
        remoteViews.setTextViewText(
            R.id.current_temp,
            data.main.temp.toString() + " " + context.getString(R.string.wi_celsius)
        )
        val imageId = getImage(
            WeatherIcon(data.weather.get(0).icon, data.weather.get(0).id),
            context
        )
        remoteViews.setImageViewResource(R.id.WeatherIcon, imageId)


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }


    fun WeatherIcon(iconName: String, mainId: Int): String {

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

    fun getLocation(context: Context): LocationUiEntity {
        val prefrence = context.getSharedPreferences("LocationSetting", Context.MODE_PRIVATE)
        val LocationLat = prefrence.getString("LocationLat", "40.712776")
        val LocationLog = prefrence.getString("LocationLog", " -74.005974")
        return LocationUiEntity(LocationLat?.toDouble()!!, LocationLog?.toDouble()!!, "", "")
    }


}






