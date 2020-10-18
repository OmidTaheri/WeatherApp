package ir.omidtaheri.remote.mapper

import ir.omidtaheri.data.entity.currentEntity.currentWeatherDataEntity
import ir.omidtaheri.remote.entity.response.currentWeather.*
import javax.inject.Inject

class CurrentWeatherResponseToDataEntityMapper @Inject constructor() :
    ResponseToDataEntityMapper<currentWeatherResponse, currentWeatherDataEntity> {
    override fun mapFromDTO(from: currentWeatherResponse): currentWeatherDataEntity {

        return currentWeatherDataEntity(
            mapFromCoordDTO(from.coord),
            mapFromMainDTO(from.main),
            from.name,
            mapFromWeatherListDTO(from.weather),
            mapFromWindDTO(from.wind),
            from.timezone
        )
    }


    fun mapFromCoordDTO(coord: Coord): ir.omidtaheri.data.entity.currentEntity.Coord {

        return ir.omidtaheri.data.entity.currentEntity.Coord(coord.lat, coord.lon)
    }

    fun mapFromMainDTO(main: Main): ir.omidtaheri.data.entity.currentEntity.Main {
        return ir.omidtaheri.data.entity.currentEntity.Main(
            main.feels_like,
            main.humidity,
            main.pressure,
            main.temp,
            main.temp_max,
            main.temp_min
        )
    }

    fun mapFromWeatherListDTO(weather: List<Weather>): List<ir.omidtaheri.data.entity.currentEntity.Weather> {

        return weather.map {
            ir.omidtaheri.data.entity.currentEntity.Weather(it.description, it.icon, it.id, it.main)
        }
    }

    fun mapFromWindDTO(wind: Wind): ir.omidtaheri.data.entity.currentEntity.Wind {
        return ir.omidtaheri.data.entity.currentEntity.Wind(wind.speed)
    }
}