package ir.omidtaheri.mainpage.widget.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import ir.omidtaheri.mainpage.widget.WeatherAppWidget


object AlarmManagerUtils {

    const val WEATHER_WIDGET_UPDATE = "ir.omidtaheri.weatherapp.widget.update"
    const val AlarmManager_REPEAT_ALARM_WIDGET_UPDATE = 1
    const val AlarmManager_SINGLE_ALARM_WIDGET_UPDATE =2


    fun startRepeatedAlarm(context: Context, interval: Long) {

        var alarmIntent = Intent(WEATHER_WIDGET_UPDATE)
        alarmIntent.setClass(context, WeatherAppWidget::class.java)

        var pendingIntent = PendingIntent.getBroadcast(
            context,
            AlarmManager_REPEAT_ALARM_WIDGET_UPDATE,
            alarmIntent,
            0
        )

        val manager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        manager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            interval,
            pendingIntent
        )

    }


    fun cancelRepeatedAlarm(context: Context) {

        var alarmIntent = Intent(WEATHER_WIDGET_UPDATE)
        alarmIntent.setClass(context, WeatherAppWidget::class.java)

        var pendingIntent = PendingIntent.getBroadcast(
            context,
            AlarmManager_REPEAT_ALARM_WIDGET_UPDATE,
            alarmIntent,
            0
        )

        val manager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        manager.cancel(pendingIntent)

    }


    fun startSingleAlarm(context: Context) {

        var alarmIntent = Intent(WEATHER_WIDGET_UPDATE)
        alarmIntent.setClass(context, WeatherAppWidget::class.java)

        var pendingIntent = PendingIntent.getBroadcast(
            context,
            AlarmManager_SINGLE_ALARM_WIDGET_UPDATE,
            alarmIntent,
            0
        )

        val manager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        manager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + (1 * 1000),
            pendingIntent
        )
    }
}