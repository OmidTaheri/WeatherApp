package ir.omidtaheri.androidbase.Utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat


object TimeUtils {

    fun getCurrentTimeMillis() = System.currentTimeMillis()

    fun getCurrentDataTime() = DateTime()

    fun getDataTimeByMillis(millis: Long) = DateTime(millis)

    fun getTimeZoneFromOffsetSeconds(offsetSeconds: Int): DateTimeZone {

        val offsetMillis: Int = offsetSeconds * 1000

        return DateTimeZone.forOffsetMillis(offsetMillis)
    }

    fun setTimeZoneToDateTime(time: DateTime, timeZone: DateTimeZone): DateTime {
        return time.withZone(timeZone)
    }


    fun dateTimeFormatter(time: DateTime, pattern: String): String {
        val formatter = DateTimeFormat.forPattern(pattern)
        return time.toString(formatter)
    }

    fun getCommonPattern(num: Int): String {

        return when (num) {
            1 -> "EEEE" //FullName Day Of Week
            2 -> "MMM"  //ShortName Month Of Year
            3 -> "dd" //Number  Day Of Month
            4 -> "EEEE , MMM dd" //Full Date
            5 -> "HH : mm" //Full Time
            6 -> "EEEE , MMM dd , HH : mm" //Full Date and Time
            7 -> "EEEE , HH : mm" //Full Date and Time
            else-> "EEE , HH : mm" //Full Date and Time
        }
    }
}