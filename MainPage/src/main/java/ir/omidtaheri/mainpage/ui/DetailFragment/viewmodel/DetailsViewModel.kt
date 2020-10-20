package ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel

import android.app.Application
import ir.omidtaheri.androidbase.BaseViewModel
import ir.omidtaheri.androidbase.Utils.TimeUtils
import ir.omidtaheri.domain.datastate.DataState
import ir.omidtaheri.domain.datastate.MessageHolder

class DetailsViewModel(
    application: Application
) :
    BaseViewModel(application) {


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


    fun getTimeInTimeZone(timezone: Int, dt: Int): String {

        val timeStampMilis: Long = (dt.toLong() * 1000)

        val loacalTime = TimeUtils.getDataTimeByMillis(timeStampMilis)
        val timeZone = TimeUtils.getTimeZoneFromOffsetSeconds(timezone)
        val MainTime = TimeUtils.setTimeZoneToDateTime(loacalTime, timeZone)
        return TimeUtils.dateTimeFormatter(MainTime, TimeUtils.getCommonPattern(8))
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
