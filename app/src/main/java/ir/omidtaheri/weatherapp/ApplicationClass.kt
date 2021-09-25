package ir.omidtaheri.weatherapp

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import ir.omidtaheri.daggercore.di.ApplicationComponentProvider
import ir.omidtaheri.daggercore.di.components.ApplicationComponent
import ir.omidtaheri.daggercore.di.components.DaggerApplicationComponent


class ApplicationClass : MultiDexApplication(), ApplicationComponentProvider {

    private lateinit var applicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        this.applicationComponent =
            DaggerApplicationComponent.factory().create(
                this,
                BuildConfig.MAPBOX_BASE_URL,
                BuildConfig.BASE_URL,
                BuildConfig.Mapbox_API_KEY,
                BuildConfig.API_KEY
            )

        applicationComponent.inject(this)


    }

    override fun provideApplicationComponent(): ApplicationComponent {
        return applicationComponent
    }


}
