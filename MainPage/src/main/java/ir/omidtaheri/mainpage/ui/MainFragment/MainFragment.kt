package ir.omidtaheri.mainpage.ui.MainFragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ir.omidtaheri.androidbase.BaseFragment
import ir.omidtaheri.daggercore.di.utils.DaggerInjectUtils
import ir.omidtaheri.mainpage.R
import ir.omidtaheri.mainpage.databinding.MainFragmentBinding
import ir.omidtaheri.mainpage.di.components.DaggerMainComponent
import ir.omidtaheri.mainpage.entity.LocationEntity.LocationUiEntity
import ir.omidtaheri.mainpage.entity.forecastEntity.Main
import ir.omidtaheri.mainpage.entity.forecastEntity.Weather
import ir.omidtaheri.mainpage.entity.forecastEntity.Wind
import ir.omidtaheri.mainpage.entity.forecastEntity.forecastList
import ir.omidtaheri.mainpage.ui.MainFragment.adapters.RecyclerViewAdapter
import ir.omidtaheri.mainpage.ui.MainFragment.adapters.SearchLocationAdapter
import ir.omidtaheri.mainpage.ui.MainFragment.viewmodel.MainViewModel
import ir.omidtaheri.mainpage.widget.utils.AlarmManagerUtils
import ir.omidtaheri.uibase.LoadBackgroungImage
import ir.omidtaheri.uibase.clear
import ir.omidtaheri.uibase.onDestroyGlide
import ir.omidtaheri.viewcomponents.MultiStateLargeCardview.MultiStateLargeCardview
import ir.omidtaheri.viewcomponents.MultiStatePage.MultiStatePage
import kotlinx.android.synthetic.main.bottom_sheet_search_location.view.*


class MainFragment : BaseFragment(), RecyclerViewAdapter.RecyclerAdapterCallback,
    SearchLocationAdapter.Callback {


    private lateinit var viewModel: MainViewModel
    private lateinit var _viewbinding: MainFragmentBinding

    private val viewbinding
        get() = _viewbinding!!


    private lateinit var largeCardView: MultiStateLargeCardview
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var multiStatePage: MultiStatePage
    private lateinit var searchbarContainer: TextInputLayout
    private lateinit var searchBar: TextInputEditText
    private lateinit var bottomSheet: ConstraintLayout
    private lateinit var chip_location: Chip
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var page_background: View
    private lateinit var bottom_sheet_close: ImageView

    var mainColorvibrant: Int? = null
    var mainColormuted: Int? = null

    var mainColordarkVibrant: Int? = null
    var mainColordarkMuted: Int? = null

    var mainColorlightVibrant: Int? = null
    var mainColorlightMuted: Int? = null

    var mainTimeZone: Int? = null
    var currentDay: Int? = null


    var lastLat: Double? = null
    var lastLog: Double? = null
    private lateinit var lastSearchLocationQuery: String
    lateinit var cityName: String
    lateinit var backgroundName: String


    var categorizeForecastItems: HashMap<Int, ArrayList<forecastList>>? = null
    private lateinit var locationrecyclerAdapter: SearchLocationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        BackpressHandler()
        initRecyclerViews()
        initUiColors("rs")
        viewModel.setSearchSubjectObserver()
        val savedLocation = viewModel.getLocation(requireContext())
        currentLocationChipInit(viewModel.getLocationName(requireContext())!!)
        lastLat = savedLocation.lat
        lastLog = savedLocation.log
        fetchData(lastLat!!, lastLog!!)
    }

    private fun BackpressHandler() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    if (getBottomSheetState()) {
                        resetBootomSheet()
                    } else {
                        activity?.finish()
                    }

                }
            })
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

    private fun fetchData(lat: Double, log: Double) {
        viewModel.getCurrentWeather(lat, log)
        viewModel.getForecastWeather(lat, log)
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
        bindUiComponentBootomSheet()
        setFabClickListner()

    }

    private fun setFabClickListner() {
        fab.setOnClickListener {
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }
    }

    private fun bindUiComponentBootomSheet() {
        chip_location = _viewbinding.root.chip_location
        bottomSheet = _viewbinding.root.bottom_sheet
        searchBar = _viewbinding.root.searchbar
        searchbarContainer = _viewbinding.root.searchbar_container
        multiStatePage = _viewbinding.root.MultiStatePage
        bottom_sheet_close = _viewbinding.root.closeButton

        initBottomSheetRecyclerViews()
        setBottomSheetCallback()
        setSearchBarListners()
    }

    private fun setBottomSheetCallback() {
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        fab.show()
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        fab.hide()

                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        fab.hide()
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)

        bottom_sheet_close.setOnClickListener {
            resetBootomSheet()
        }
    }


    private fun currentLocationChipInit(LocationName: String) {
        chip_location.text = LocationName
    }


    private fun initBottomSheetRecyclerViews() {
        multiStatePage.apply {
            locationrecyclerAdapter = SearchLocationAdapter(requireContext())
            locationrecyclerAdapter.setCallback(this@MainFragment)

            configRecyclerView(
                locationrecyclerAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            )
            setCustomLayoutAnimation(R.anim.layout_animation_fall_down_quick)
            toEmptyState()
        }
    }

    private fun setSearchBarListners() {

        searchBar.doOnTextChanged { text, start, before, count ->
            lastSearchLocationQuery = text.toString()
            viewModel.searchSubject.onNext(text.toString())
        }

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

            viewModel.saveLocationName(requireContext(), cityName)
            currentLocationChipInit(cityName)

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


        viewModel.LocationUiResultsLiveData.observe(this, Observer {

            if (it == null || it.size == 0) {
                multiStatePage.toEmptyState()
            } else {
                (multiStatePage.getRecyclerView().adapter as SearchLocationAdapter).addItems(it)
                multiStatePage.toDateState()
            }
        })

        viewModel.ErrorLocationResultsLiveData.observe(this, Observer {
            multiStatePage.toErrorState(View.OnClickListener {
                multiStatePage.toLoadingState()
                viewModel.searchSubject.onNext(lastSearchLocationQuery)
            })
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
                changeBootomSheetBackground()
            }
            bitmap.recycle()
        }


    }

    private fun changeBootomSheetBackground() {
        bottomSheet.setBackgroundColor(mainColordarkVibrant?.let {
            mainColordarkVibrant!!
        } ?: mainColordarkMuted!!
        )
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
        multiStatePage.toLoadingState()
        hideSoftKeyboard(searchBar)
    }

    fun hideSoftKeyboard(view: View) {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(viewbinding.root, message, BaseTransientBottomBar.LENGTH_INDEFINITE)
            .setAction(
                "Retry"
            ) {
                fetchData(lastLat!!, lastLog!!)
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

    override fun onLocationItemClick(locationUiEntity: LocationUiEntity) {
        resetBootomSheet()
        initRecyclerViews()
        largeCardView.toLoadingState()

        lastLat = locationUiEntity.lat
        lastLog = locationUiEntity.log
        viewModel.saveLocation(requireContext(), lastLat!!, lastLog!!)
        fetchData(locationUiEntity.lat, locationUiEntity.log)
        setUpdateWidgetAlarm(requireContext())
    }

    fun setUpdateWidgetAlarm(context: Context) {
        AlarmManagerUtils.startSingleAlarm(context)
    }


    private fun resetBootomSheet() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
        (multiStatePage.getRecyclerView().adapter as SearchLocationAdapter).clear()
        multiStatePage.toEmptyState()
        searchBar.setText("")
        searchBar.clearFocus()
        lastSearchLocationQuery = ""
        hideSoftKeyboard(searchBar)
    }

    fun getBottomSheetState(): Boolean {
        return when (sheetBehavior.state) {
            BottomSheetBehavior.STATE_HIDDEN -> {
                false
            }
            BottomSheetBehavior.STATE_EXPANDED -> {
                true
            }
            BottomSheetBehavior.STATE_COLLAPSED -> {
                true
            }
            BottomSheetBehavior.STATE_DRAGGING -> {
                true
            }
            BottomSheetBehavior.STATE_SETTLING -> {
                true
            }
            else -> false
        }
    }
}

