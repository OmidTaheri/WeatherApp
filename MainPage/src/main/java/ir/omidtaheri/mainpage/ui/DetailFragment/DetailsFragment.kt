package ir.omidtaheri.mainpage.ui.DetailFragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.R
import ir.omidtaheri.mainpage.databinding.DetailsFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerDetailsComponent
import ir.omidtaheri.mainpage.entity.forecastEntity.forecastList
import ir.omidtaheri.mainpage.ui.DetailFragment.adapters.ForecastDetailsAdapter
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailsViewModel
import ir.omidtaheri.uibase.LoadBackgroungImage
import ir.omidtaheri.uibase.clear
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.viewcomponents.MultiStateLargeCardview.MultiStateLargeCardview


class DetailsFragment : BaseFragment() {


    private lateinit var viewModel: DetailsViewModel


    private lateinit var _viewbinding: DetailsFragmentBinding

    private val viewbinding
        get() = _viewbinding!!


    lateinit var largeCardView: MultiStateLargeCardview
    lateinit var recyclerView: RecyclerView


    var mainColorvibrant: Int? = null
    var mainColormuted: Int? = null

    var mainColordarkVibrant: Int? = null
    var mainColordarkMuted: Int? = null

    var mainColorlightVibrant: Int? = null
    var mainColorlightMuted: Int? = null


    private lateinit var page_background: View
    lateinit var args: DetailsFragmentArgs


    lateinit var mainForecastItem: forecastList
    lateinit var forecastList: List<forecastList>
    lateinit var cityName: String
    lateinit var backgroundName: String
    var mainTimeZone: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        args = DetailsFragmentArgs.fromBundle(requireArguments())

        mainTimeZone = args.timeZone
        backgroundName = args.backgroundName
        cityName = args.cityName
        forecastList = args.forecastList.asList().sortedBy {
            it.dt
        }
        mainForecastItem = forecastList[0]
        initRecyclerViews()
        initMainCardview()
        //////initUiColors(backgroundName)

    }


    private fun initRecyclerViews() {


        recyclerView.adapter = ForecastDetailsAdapter()

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)

        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(
                viewbinding.recyclerView.context,
                R.anim.layout_animation_fall_down
            )

        recyclerView.layoutAnimation = animation


        if (forecastList.size >= 2) {
            val sublistItems = forecastList.subList(1, forecastList.size)
            (recyclerView.adapter as ForecastDetailsAdapter).addItems(sublistItems, mainTimeZone)
        } else {
            (recyclerView.adapter as ForecastDetailsAdapter).clear()
        }

    }


    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = DetailsFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {

        largeCardView = _viewbinding!!.largeCardView
        recyclerView = _viewbinding!!.recyclerView

        largeCardView.toLoadingState()
        page_background = _viewbinding!!.pageBackground

    }

    override fun setDataLiveObserver() {

    }

    override fun ConfigDaggerComponent() {
        DaggerDetailsComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)
    }

    private fun initMainCardview() {


        largeCardView.toDateState(
            cityName,
            viewModel.getTimeInTimeZone(mainTimeZone!!, mainForecastItem.dt),
            mainForecastItem.main.temp.toString() + " " + getString(R.string.wi_celsius),
            mainForecastItem.weather.get(0).main,
            mainForecastItem.main.temp_min.toString() + " " + getString(R.string.wi_celsius),
            mainForecastItem.main.temp_max.toString() + " " + getString(R.string.wi_celsius),
            viewModel.showWeatherIcon(
                mainForecastItem.weather.get(0).icon,
                mainForecastItem.weather.get(0).id
            )
        )


        var backgroundName =
            viewModel.setBackgroundImage(
                mainForecastItem.weather.get(0).main,
                mainForecastItem.weather.get(0).icon
            )

        page_background.LoadBackgroungImage(backgroundName, requireContext())
        initUiColors(backgroundName)

    }


    private fun initUiColors(backgroundName: String) {
        createPaletteAsync(getImage(backgroundName, requireContext()))
    }

    private fun createPaletteAsync(bitmap: Bitmap) {
        Palette.from(bitmap).generate {

            it?.let {

                getcolorsFromPallete(it)
                setLargeCardViewColorFromPallete()
                changeSystemBarColor()
                changeRecyclerViewAdapterColor()
            }

            bitmap.recycle()
        }

    }

    private fun changeRecyclerViewAdapterColor() {

        var colorList = arrayListOf<Int>()

        colorList.add(Color.rgb(255, 255, 255))//White

        mainColordarkVibrant?.let {
            colorList.add(mainColordarkVibrant!!)
        } ?: colorList.add(mainColordarkMuted!!)


        (recyclerView.adapter as ForecastDetailsAdapter).setItemColor(colorList)


    }

    private fun setLargeCardViewColorFromPallete() {

        var colorList = arrayListOf<Int>()

        mainColorvibrant?.let {
            colorList.add(mainColorvibrant!!)
        }
            ?: colorList.add(mainColormuted!!)

        mainColordarkVibrant?.let {
            colorList.add(mainColordarkVibrant!!)
        } ?: colorList.add(mainColordarkMuted!!)


        mainColorlightVibrant?.let {
            colorList.add(mainColorlightVibrant!!)
        } ?: colorList.add(mainColorlightMuted!!)


        largeCardView.setColor(colorList)

    }

    private fun getcolorsFromPallete(pallete: Palette) {
        val vibrantSwatch = pallete.vibrantSwatch
        val mutedSwatch = pallete.mutedSwatch
        val darkVibrantSwatch = pallete.darkVibrantSwatch
        val darkMutedSwatch = pallete.darkMutedSwatch
        val lightVibrantSwatch = pallete.lightVibrantSwatch
        val lightMutedSwatch = pallete.lightMutedSwatch

        mainColorvibrant = vibrantSwatch?.rgb

        mainColordarkVibrant = darkVibrantSwatch?.rgb

        mainColorlightVibrant = lightVibrantSwatch?.rgb

        mainColordarkMuted = darkMutedSwatch?.rgb

        mainColormuted = mutedSwatch?.rgb

        mainColorlightMuted = lightMutedSwatch?.rgb

    }


    private fun getImage(imageName: String, context: Context): Bitmap {
        val drawableId =
            getResources().getIdentifier(imageName, "drawable", context.getPackageName())
        val options = BitmapFactory.Options()
        options.inSampleSize = 15
        return BitmapFactory.decodeResource(resources, drawableId, options)
    }


    private fun changeSystemBarColor() {

        val window: Window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)


        mainColordarkVibrant?.let {
            window.setStatusBarColor(mainColordarkVibrant!!)
            window.setNavigationBarColor(mainColordarkVibrant!!)
        } ?: changeSystemBarColorByOtherColor()


    }

    fun changeSystemBarColorByOtherColor() {
        val window: Window = requireActivity().window
        window.statusBarColor = mainColordarkMuted!!
        window.navigationBarColor = mainColordarkMuted!!
    }

    override fun setSnackBarMessageLiveDataObserver() {
        viewModel.MessageSnackBar.observe(this, Observer {
            showSnackBar(it)
        })
    }

    override fun setToastMessageLiveDataObserver() {
        viewModel.MessageToast.observe(this, Observer {
            showToast(it)
        })
    }

    override fun setSnackBarErrorLivaDataObserver() {
        viewModel.ErrorSnackBar.observe(this, Observer {
            showSnackBar(it)
        })
    }

    override fun setToastErrorLiveDataObserver() {
        viewModel.ErrorToast.observe(this, Observer {
            showToast(it)
        })
    }

    override fun setLoadingLiveDataObserver() {
        viewModel.isLoading.observe(this, Observer {
            showLoading(it)
        })
    }

    override fun showLoading(show: Boolean) {
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(viewbinding.root, message, BaseTransientBottomBar.LENGTH_INDEFINITE)
            .show()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showDialog(message: String) {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        page_background?.clear()
        page_background?.background = null
        onDestroyGlide()
    }

}
