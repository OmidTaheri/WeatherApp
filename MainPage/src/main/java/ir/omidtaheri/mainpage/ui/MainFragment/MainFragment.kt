package ir.omidtaheri.mainpage.ui.MainFragment

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
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.R
import ir.omidtaheri.mainpage.databinding.MainFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerMainComponent
import ir.omidtaheri.mainpage.entity.forecastEntity.Main
import ir.omidtaheri.mainpage.entity.forecastEntity.Weather
import ir.omidtaheri.mainpage.entity.forecastEntity.Wind
import ir.omidtaheri.mainpage.entity.forecastEntity.forecastList
import ir.omidtaheri.mainpage.ui.MainFragment.adapters.RecyclerViewAdapter
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModel
import ir.omidtaheri.uibase.LoadBackgroungImage
import ir.omidtaheri.uibase.clear
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.viewcomponents.MultiStateLargeCardview.MultiStateLargeCardview


class MainFragment : BaseFragment(), RecyclerViewAdapter.RecyclerAdapterCallback {


    private lateinit var viewModel: MainViewModel


    private lateinit var _viewbinding: MainFragmentBinding

    private val viewbinding
        get() = _viewbinding!!


    lateinit var largeCardView: MultiStateLargeCardview
    lateinit var recyclerView: RecyclerView
    lateinit var fab: FloatingActionButton


    var mainColorvibrant: Int? = null
    var mainColormuted: Int? = null

    var mainColordarkVibrant: Int? = null
    var mainColordarkMuted: Int? = null

    var mainColorlightVibrant: Int? = null
    var mainColorlightMuted: Int? = null

    var mainTimeZone: Int? = null
    var currentDay: Int? = null

    lateinit var cityName: String
    lateinit var backgroundName: String

    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var page_background: View

    var categorizeForecastItems: HashMap<Int, ArrayList<forecastList>>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initRecyclerViews()
        initUiColors("rs")
        fetchData()
    }


    private fun initRecyclerViews() {


        recyclerView.adapter = RecyclerViewAdapter(this)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)

        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(
                viewbinding.recyclerView.context,
                R.anim.layout_animation_fall_down
            )

        recyclerView.layoutAnimation = animation

        val emptyForecastList = forecastList(
            0,
            "",
            Main(0.0, 0, 0, 0.0, 0.0, 0.0),
            listOf<Weather>(),
            Wind(0.0)
        )


        val loadingList: List<forecastList> = listOf(
            emptyForecastList,
            emptyForecastList,
            emptyForecastList,
            emptyForecastList,
            emptyForecastList

        )
        (recyclerView.adapter as RecyclerViewAdapter).addItems(loadingList, null)
    }

    private fun fetchData() {
        viewModel.getCurrentWeather(40.712776, -74.005974)
        viewModel.getForecastWeather(40.712776, -74.005974)
    }

    override fun InflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        _viewbinding = MainFragmentBinding.inflate(inflater, container, false)
        val view = viewbinding.root
        return view
    }

    override fun bindUiComponent() {

        largeCardView = _viewbinding!!.largeCardView
        recyclerView = _viewbinding!!.recyclerView
        fab = _viewbinding!!.fab
        largeCardView.toLoadingState()
        page_background = _viewbinding!!.pageBackground
        bottomAppBar = _viewbinding.bottomAppBar
    }

    override fun ConfigDaggerComponent() {
        DaggerMainComponent
            .builder()
            .applicationComponent(DaggerInjectUtils.provideApplicationComponent(requireContext().applicationContext))
            .build()
            .inject(this)
    }

    override fun SetViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun setDataLiveObserver() {

        viewModel.currentWeatherLiveData.observe(this, Observer {

            mainTimeZone = it.timezone
            cityName = it.name

            largeCardView.toDateState(
                it.name,
                viewModel.getCurrentTimeInTimeZone(it.timezone),
                it.main.temp.toString() + " " + getString(R.string.wi_celsius),
                it.weather.get(0).main,
                it.main.temp_min.toString() + " " + getString(R.string.wi_celsius),
                it.main.temp_max.toString() + " " + getString(R.string.wi_celsius),
                viewModel.showWeatherIcon(it.weather.get(0).icon, it.weather.get(0).id)
            )


            backgroundName =
                viewModel.setBackgroundImage(it.weather.get(0).main, it.weather.get(0).icon)

            page_background.LoadBackgroungImage(backgroundName, requireContext())
            initUiColors(backgroundName)
        })

        viewModel.forecastWeatherLiveData.observe(this, Observer {

            mainTimeZone = it.city.timezone

            categorizeForecastItems =
                viewModel.categorizeForecastItems(it, mainTimeZone, currentDay)

            val filteredItems = viewModel.filterForecastItems(categorizeForecastItems!!)

            (recyclerView.adapter as RecyclerViewAdapter).addItems(filteredItems, it.city.timezone)

        })

        viewModel.dayOfWeekLiveData.observe(this, Observer {
            currentDay = it
        })

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
                changeAppbarColor()
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


        (recyclerView.adapter as RecyclerViewAdapter).setItemColor(colorList)


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

    private fun changeAppbarColor() {

        mainColorvibrant?.let {
            bottomAppBar.setBackgroundColor(mainColorvibrant!!)
        } ?: bottomAppBar.setBackgroundColor(mainColormuted!!)

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
            .setAction(
                "Retry"
            ) {
                fetchData()
            }.show()
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

    override fun onClickItem(dt: Int) {
        val dayOfWeek = viewModel.getDayOfTimeStamp(mainTimeZone!!, dt)
        val oneDayForecast = categorizeForecastItems?.get(dayOfWeek)

        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(
            oneDayForecast?.toTypedArray()!!,
            mainTimeZone!!, backgroundName, cityName
        )
        findNavController().navigate(action)

    }
}
