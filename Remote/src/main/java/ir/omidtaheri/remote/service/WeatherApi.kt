package ir.omidtaheri.remote.service

import io.reactivex.Single
import ir.omidtaheri.remote.entity.response.currentWeather.CurrentWeatherResponse
import ir.omidtaheri.remote.entity.response.forecastWeather.ForecastWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {


    @GET("data/2.5/weather")
    fun CurrentWeatherByName(
        @Query("q") city_name: String,
        @Query("units") units: String?
    ): Single<CurrentWeatherResponse>


    @GET("data/2.5/weather")
    fun CurrentWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String?
    ): Single<CurrentWeatherResponse>


    @GET("data/2.5/forecast")
    fun ForecastWeatherByName(
        @Query("q") name: String,
        @Query("units") units: String?,
        @Query("cnt") cnt: Int?

    ): Single<ForecastWeatherResponse>


    @GET("data/2.5/forecast")
    fun ForecastWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String?,
        @Query("cnt") cnt: Int?
    ): Single<ForecastWeatherResponse>


}
