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


class MultiStateLargeCardview(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private val viewBinding: MultiStateLargeCardviewBinding =
        MultiStateLargeCardviewBinding.inflate(LayoutInflater.from(context), this, true)

    private fun dataVisibility(show: Boolean) {

        if (show) {
            viewBinding.dataGroup.visibility = View.VISIBLE
        } else {
            viewBinding.dataGroup.visibility = View.GONE
        }
    }

    private fun progressBarVisibility(show: Boolean) {

        if (show) {
            viewBinding.progressBar.visibility = View.VISIBLE
        } else {
            viewBinding.progressBar.visibility = View.GONE
        }
    }


    private fun setCityText(text: String) {
        viewBinding.root.city.text = text
    }


    private fun setDateText(text: String) {
        viewBinding.root.date.text = text
    }


    private  fun setTempText(text: String) {
        viewBinding.root.temp.text = text
    }


    private fun setSatusText(text: String) {
        viewBinding.root.status.text = text
    }


    private fun setMinTempText(text: String) {
        viewBinding.root.min_temp.text = text
    }

    private  fun setMaxTempText(text: String) {
        viewBinding.root.max_temp.text = text
    }

    private fun setIconImage(path: String) {
        viewBinding.root.IconImageView.LoadWeatherIcon(path, context)
    }

    fun setColor(
        colorList: List<Int>
    ) {
        viewBinding.root.city.setTextColor(colorList[1])
        viewBinding.root.date.setTextColor(colorList[1])
        viewBinding.root.temp.setTextColor(colorList[1])
        viewBinding.root.min_temp.setTextColor(colorList[1])
        viewBinding.root.max_temp.setTextColor(colorList[1])

        viewBinding.root.IconImageView.setColorFilter(
            colorList[0],
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
        viewBinding.root.status.setTextColor(colorList[1])

        setCardBackGroundColor(colorList[2], 220)


    }

    fun toLoadingState() {
        dataVisibility(false)
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

        dataVisibility(true)
        progressBarVisibility(false)
    }


    private fun setCardBackGroundColor(@ColorInt color: Int, alpha: Int) {
        val argbColor = adjustAlpha(color, alpha)
        viewBinding.cardviewParent.setCardBackgroundColor(argbColor)
    }

}
