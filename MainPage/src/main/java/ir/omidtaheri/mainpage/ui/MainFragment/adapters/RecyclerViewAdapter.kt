package ir.omidtaheri.mainpage.ui.MainFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseViewHolder
import ir.omidtaheri.androidbase.Utils.TimeUtils
import ir.omidtaheri.mainpage.R
import ir.omidtaheri.mainpage.databinding.ForecastWeatherItemBinding
import ir.omidtaheri.mainpage.entity.forecastEntity.forecastList

class RecyclerViewAdapter(val callBack: RecyclerAdapterCallback) :
    RecyclerView.Adapter<BaseViewHolder>() {

    var items: MutableList<forecastList> = mutableListOf()

    var titleColor: Int? = null
    var backgroundColor: Int? = null
    var timeZone: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder(
            ForecastWeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    interface RecyclerAdapterCallback {
        fun onClickItem(dt: Int)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    //    Helpers
    fun addItem(item: forecastList, timeZone: Int?) {
        this.timeZone = timeZone
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(list: List<forecastList>, timeZone: Int?) {
        clear()
        this.timeZone = timeZone
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun remove(item: forecastList) {
        val index = items.indexOf(item)
        if (index >= 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ForecastWeatherItemBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            val forecastItem = items.get(position)

            binding.apply {
                recyclerItemParent.setColor(titleColor, backgroundColor)

                if (forecastItem.dt == 0) {
                    recyclerItemParent.toLoadingState()
                } else {
                    recyclerItemParent.toDateState(
                        forecastItem.main.temp_min.toString() + " " + binding.root.context.getString(
                            R.string.wi_celsius
                        ),
                        forecastItem.main.temp_max.toString() + " " + binding.root.context.getString(
                            R.string.wi_celsius
                        ),
                        showWeatherIcon(
                            forecastItem.weather.get(0).icon,
                            forecastItem.weather.get(0).id
                        ),
                        timeZone?.let {
                            getTimeInTimeZone(it, forecastItem.dt)
                        } ?: ""

                    )

                    callBack.onClickItem(forecastItem.dt)
                }


            }
        }
    }


    private fun getTimeInTimeZone(timezone: Int, dt: Int): String {

        val timeStampMilis: Long = (dt.toLong() * 1000)

        val loacalTime = TimeUtils.getDataTimeByMillis(timeStampMilis)
        val timeZone = TimeUtils.getTimeZoneFromOffsetSeconds(timezone)
        val MainTime = TimeUtils.setTimeZoneToDateTime(loacalTime, timeZone)
        return TimeUtils.dateTimeFormatter(MainTime, TimeUtils.getCommonPattern(1))
    }


    fun setItemColor(colorList: List<Int>) {
        this.titleColor = colorList.get(0)
        this.backgroundColor = colorList.get(1)
        notifyDataSetChanged()
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


}
