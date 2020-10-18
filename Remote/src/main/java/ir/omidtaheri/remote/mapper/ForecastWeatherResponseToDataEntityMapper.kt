package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.forecastEntity.forecastWeatherDataEntity
import ir.omidtaheri.remote.entity.response.forecastWeather.Coord
import ir.omidtaheri.remote.entity.response.forecastWeather.Main
import ir.omidtaheri.remote.entity.response.forecastWeather.Weather
import ir.omidtaheri.remote.entity.response.forecastWeather.Wind
import ir.omidtaheri.remote.entity.response.forecastWeather.City
import ir.omidtaheri.remote.entity.response.forecastWeather.forecastList
import ir.omidtaheri.remote.entity.response.forecastWeather.forecastWeatherResponse
import javax.inject.Inject

class ForecastWeatherResponseToDataEntityMapper @Inject constructor() :
    ResponseToDataEntityMapper<forecastWeatherResponse, forecastWeatherDataEntity> {

    override fun mapFromDTO(from: forecastWeatherResponse): forecastWeatherDataEntity {

        return forecastWeatherDataEntity(
            mapFromCityDTO(from.city),
            mapFromForecastListDTO(from.list)
        )

    }

    fun mapFromCityDTO(city: City): ir.omidtaheri.data.entity.forecastEntity.City {

        return ir.omidtaheri.data.entity.forecastEntity.City(mapFromCoordDTO(city.coord), city.name,city.timezone)
    }

    fun mapFromCoordDTO(coord: Coord): ir.omidtaheri.data.entity.forecastEntity.Coord {

        return ir.omidtaheri.data.entity.forecastEntity.Coord(coord.lat, coord.lon)
    }

    fun mapFromForecastListDTO(forecastList: List<forecastList>): List<ir.omidtaheri.data.entity.forecastEntity.forecastList> {

        return forecastList.map {
            ir.omidtaheri.data.entity.forecastEntity.forecastList(
                it.dt,
                it.dt_txt,
                mapFromMainDTO(it.main),
                mapFromWeatherListDTO(it.weather),
                mapFromWindDTO(it.wind)
            )
        }
    }

    fun mapFromWindDTO(wind: Wind): ir.omidtaheri.data.entity.forecastEntity.Wind {
        return ir.omidtaheri.data.entity.forecastEntity.Wind(wind.speed)
    }

    fun mapFromMainDTO(main: Main): ir.omidtaheri.data.entity.forecastEntity.Main {
        return ir.omidtaheri.data.entity.forecastEntity.Main(
            main.feels_like,
            main.humidity,
            main.pressure,
            main.temp,
            main.temp_max,
            main.temp_min
        )
    }

    fun mapFromWeatherListDTO(weather: List<Weather>): List<ir.omidtaheri.data.entity.forecastEntity.Weather> {

        return weather.map {
            ir.omidtaheri.data.entity.forecastEntity.Weather(
                it.description,
                it.icon,
                it.id,
                it.main
            )
        }
    }

}