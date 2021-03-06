package ir.omidtaheri.mainpage.entity.forecastEntity

import android.os.Parcel
import android.os.Parcelable


data class forecastList(
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable<Main>(Main::class.java.classLoader)!!,
        parcel.createTypedArrayList(Weather)!!,
        parcel.readParcelable<Wind>(Wind::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(dt)
        parcel.writeString(dt_txt)
        parcel.writeParcelable(main, flags)
        parcel.writeTypedList(weather)
        parcel.writeParcelable(wind, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<forecastList> {
        override fun createFromParcel(parcel: Parcel): forecastList {
            return forecastList(parcel)
        }

        override fun newArray(size: Int): Array<forecastList?> {
            return arrayOfNulls(size)
        }
    }
}

