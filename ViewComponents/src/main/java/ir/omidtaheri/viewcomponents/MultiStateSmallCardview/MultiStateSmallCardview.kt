package ir.omidtaheri.viewcomponents.MultiStateSmallCardview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import ir.omidtaheri.uibase.LoadWeatherIcon
import ir.omidtaheri.uibase.adjustAlpha
import ir.omidtaheri.viewcomponents.databinding.MultiStateSmallCardviewBinding
import kotlinx.android.synthetic.main.multi_state_large_cardview.view.*
import kotlinx.android.synthetic.main.multi_state_small_cardview.view.*
import kotlinx.android.synthetic.main.multi_state_small_cardview.view.IconImageView
import kotlinx.android.synthetic.main.multi_state_small_cardview.view.date
import kotlinx.android.synthetic.main.multi_state_small_cardview.view.max_temp
import kotlinx.android.synthetic.main.multi_state_small_cardview.view.min_temp


class MultiStateSmallCardview(context: Context?, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private val viewbinding: MultiStateSmallCardviewBinding =
        MultiStateSmallCardviewBinding.inflate(LayoutInflater.from(context), this, true)

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


    private fun setMinTempText(text: String) {
        viewbinding.root.min_temp.text = text
    }

    private  fun setMaxTempText(text: String) {
        viewbinding.root.max_temp.text = text
    }

    private fun setDateText(text: String) {
        viewbinding.root.date.text = text
    }

    private fun setIconImage(path: String) {
        viewbinding.root.IconImageView.LoadWeatherIcon(path, context)
    }


    fun toLoadingState() {
        DataVisibility(false)
        progressBarVisibility(true)
    }


    fun toDateState(minTemp: String, maxTemp: String, imagPpath: String, data: String) {
        setMinTempText(minTemp)
        setMaxTempText(maxTemp)
        setIconImage(imagPpath)
        setDateText(data)
        DataVisibility(true)
        progressBarVisibility(false)
    }


    fun setColor(titleColor: Int?, backgroundColor: Int?) {

        titleColor?.let {

            viewbinding.root.date.setTextColor(titleColor)
            viewbinding.root.min_temp.setTextColor(titleColor)
            viewbinding.root.max_temp.setTextColor(titleColor)

            viewbinding.root.IconImageView.setColorFilter(
                titleColor,
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }

        backgroundColor?.let {
            setCardBackGroundColor(backgroundColor, 180)
        }


    }


    private fun setCardBackGroundColor(@ColorInt color: Int, alpha: Int) {
        val argbColor = adjustAlpha(color, alpha)
        viewbinding.cardviewParent.setCardBackgroundColor(argbColor)
    }

}
