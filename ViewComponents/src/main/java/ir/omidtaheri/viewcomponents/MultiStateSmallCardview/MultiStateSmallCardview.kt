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
import kotlinx.android.synthetic.main.multi_state_small_cardview.view.*


class MultiStateSmallCardview(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private val viewBinding: MultiStateSmallCardviewBinding =
        MultiStateSmallCardviewBinding.inflate(LayoutInflater.from(context), this, true)

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


    private fun setMinTempText(text: String) {
        viewBinding.root.min_temp.text = text
    }

    private fun setMaxTempText(text: String) {
        viewBinding.root.max_temp.text = text
    }

    private fun setDateText(text: String) {
        viewBinding.root.date.text = text
    }

    private fun setIconImage(path: String) {
        viewBinding.root.IconImageView.LoadWeatherIcon(path, context)
    }


    fun toLoadingState() {
        dataVisibility(false)
        progressBarVisibility(true)
    }


    fun toDateState(minTemp: String, maxTemp: String, imagPpath: String, data: String) {
        setMinTempText(minTemp)
        setMaxTempText(maxTemp)
        setIconImage(imagPpath)
        setDateText(data)
        dataVisibility(true)
        progressBarVisibility(false)
    }


    fun setColor(titleColor: Int?, backgroundColor: Int?) {

        titleColor?.let {

            viewBinding.root.date.setTextColor(titleColor)
            viewBinding.root.min_temp.setTextColor(titleColor)
            viewBinding.root.max_temp.setTextColor(titleColor)

            viewBinding.root.IconImageView.setColorFilter(
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
        viewBinding.cardviewParent.setCardBackgroundColor(argbColor)
    }

}
