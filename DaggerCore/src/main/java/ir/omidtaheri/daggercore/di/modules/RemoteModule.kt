package ir.omidtaheri.daggercore.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import ir.omidtaheri.daggercore.BuildConfig
import ir.omidtaheri.daggercore.di.qualifiers.MapboxIntercepter
import ir.omidtaheri.daggercore.di.qualifiers.MapboxRetrofit
import ir.omidtaheri.daggercore.di.qualifiers.WeatherIntercepter
import ir.omidtaheri.daggercore.di.qualifiers.WeatherRetrofit
import ir.omidtaheri.remote.service.MapboxApi
import ir.omidtaheri.remote.service.WeatherApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object RemoteModule {

    @Singleton
    @Provides
    @WeatherIntercepter
    fun provideWeatherInterceptors(@Named("apiKey") apiKey: String): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val keyInterceptor = Interceptor { chain ->

            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("appid", apiKey)
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            return@Interceptor chain.proceed(request)
        }

        interceptors.add(keyInterceptor)
        return interceptors
    }

    @Singleton
    @Provides
    @MapboxIntercepter
    fun provideMapboxInterceptors(@Named("mapBoxApiKey") mapBoxApiKey: String): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val keyInterceptor = Interceptor { chain ->

            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("access_token", mapBoxApiKey)
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            return@Interceptor chain.proceed(request)
        }

        interceptors.add(keyInterceptor)
        return interceptors
    }

    @Singleton
    @Provides
    @MapboxRetrofit
    fun provideMapboxRetrofit(
        @MapboxIntercepter interceptors: ArrayList<Interceptor>,
        @Named("mapboxBaseUrl") mapboxBaseUrl: String
    ): Retrofit {

        val clientBuilder = OkHttpClient.Builder()
        if (!interceptors.isEmpty()) {
            interceptors.forEach { interceptor ->
                clientBuilder.addInterceptor(interceptor)
            }
        }

        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(StethoInterceptor())
        }

        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mapboxBaseUrl)
            .build()
    }

    @Singleton
    @Provides
    @WeatherRetrofit
    fun provideRetrofit(
        @WeatherIntercepter interceptors: ArrayList<Interceptor>,
        @Named("url") baseUrl: String
    ): Retrofit {

        val clientBuilder = OkHttpClient.Builder()
        if (!interceptors.isEmpty()) {
            interceptors.forEach { interceptor ->
                clientBuilder.addInterceptor(interceptor)
            }
        }

        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(StethoInterceptor())
        }

        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherApi(@WeatherRetrofit retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMapboxApi(@MapboxRetrofit retrofit: Retrofit): MapboxApi {
        return retrofit.create(MapboxApi::class.java)
    }
}
