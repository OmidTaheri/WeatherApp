package ir.omidtaheri.weatherapp

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import ir.omidtaheri.daggercore.di.ApplicationComponentProvider
import ir.omidtaheri.daggercore.di.components.ApplicationComponent
import ir.omidtaheri.daggercore.di.components.DaggerApplicationComponent
import ir.omidtaheri.daggercore.di.modules.ApplicationModule
import ir.omidtaheri.daggercore.di.modules.RemoteModule
import ir.omidtaheri.daggercore.di.modules.RepositoryModule


class ApplicationClass : MultiDexApplication(), ApplicationComponentProvider {

    lateinit var applicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        this.applicationComponent =
            DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .remoteModule(RemoteModule(BuildConfig.BASE_URL, BuildConfig.API_KEY))
                .repositoryModule(RepositoryModule())
                .build()

        applicationComponent.inject(this)

    }

    override fun provideApplicationComponent(): ApplicationComponent {
        return applicationComponent
    }


}
