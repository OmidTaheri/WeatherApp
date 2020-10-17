package ir.omidtaheri.viewcomponents.MultiStateLargeCardview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import ir.omidtaheri.uibase.LoadWeatherIcon
import ir.omidtaheri.uibase.adjustAlpha
import ir.omidtaheri.viewcomponents.databinding.MultiStateLargeCardviewBinding
import kotlinx.android.synthetic.main.multi_state_large_cardview.view.*
import kotlinx.android.synthetic.main.multi_state_small_cardview.view.IconImageView
import kotlinx.android.synthetic.main.multi_state_small_cardview.view.max_temp
import kotlinx.android.synthetic.main.multi_state_small_cardview.view.min_temp


class MultiStateLargeCardview(context: Context?, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private val viewbinding: MultiStateLargeCardviewBinding =
        MultiStateLargeCardviewBinding.inflate(LayoutInflater.from(context), this, true)

    private fun DataVisibility(show: Boolean) {

        if (show) {
            viewbinding.dataGroup.visibility = View.VISIBLE
        } else {
            viewbinding.dataGroup.visibility = View.GONE
        }
    }

    private fun progressBarVisibility(show: Boolean) {

        if (show) {
            viewbinding.progressBar.visibility = View.VISIBLE
        } else {
            viewbinding.progressBar.visibility = View.GONE
        }
    }


    private fun setCityText(text: String) {
        viewbinding.root.city.text = text
    }


    private fun setDateText(text: String) {
        viewbinding.root.date.text = text
    }


    private  fun setTempText(text: String) {
        viewbinding.root.temp.text = text
    }


    private fun setSatusText(text: String) {
        viewbinding.root.status.text = text
    }


    private fun setMinTempText(text: String) {
        viewbinding.root.min_temp.text = text
    }

    private  fun setMaxTempText(text: String) {
        viewbinding.root.max_temp.text = text
    }

    private fun setIconImage(path: String) {
        viewbinding.root.IconImageView.LoadWeatherIcon(path, context)
    }

    fun setColor(
        colorList: List<Int>
    ) {
        viewbinding.root.city.setTextColor(colorList.get(1))
        viewbinding.root.date.setTextColor(colorList.get(1))
        viewbinding.root.temp.setTextColor(colorList.get(1))
        viewbinding.root.min_temp.setTextColor(colorList.get(1))
        viewbinding.root.max_temp.setTextColor(colorList.get(1))

        viewbinding.root.IconImageView.setColorFilter(
            colorList.get(0),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
        viewbinding.root.status.setTextColor(colorList.get(1))

        setCardBackGroundColor(colorList.get(2), 220)


    }

    fun toLoadingState() {
        DataVisibility(false)
        progressBarVisibility(true)
    }


    fun toDateState(
        city: String,
        date: String,
        temp: String,
        satus: String,
        minTemp: String,
        maxTemp: String,
        imagPpath: String
    ) {
        setCityText(city)
        setDateText(date)
        setTempText(temp)
        setSatusText(satus)
        setMinTempText(minTemp)
        setMaxTempText(maxTemp)
        setIconImage(imagPpath)

        DataVisibility(true)
        progressBarVisibility(false)
    }


    private fun setCardBackGroundColor(@ColorInt color: Int, alpha: Int) {
        val argbColor = adjustAlpha(color, alpha)
        viewbinding.cardviewParent.setCardBackgroundColor(argbColor)
    }

}
