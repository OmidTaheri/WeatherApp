package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.currentEntity.*
import ir.omidtaheri.domain.entity.currentEntity.currentWeatherDomainEntity

import javax.inject.Inject

class CurrentEntityDomainDataMapper @Inject constructor() :
    DomainDataMapper<currentWeatherDataEntity, currentWeatherDomainEntity> {
    override fun mapFromDataEntity(from: currentWeatherDataEntity): currentWeatherDomainEntity {

        return currentWeatherDomainEntity(
            mapFromCoordData(from.coord),
            mapFromMainData(from.main),
            from.name,
            mapFromWeatherListData(from.weather),
            mapFromWindData(from.wind),
            from.timezone
        )
    }

    override fun mapToDataEntity(from: currentWeatherDomainEntity): currentWeatherDataEntity {
        return currentWeatherDataEntity(
            mapToCoordData(from.coord),
            mapToMainData(from.main),
            from.name,
            mapToWeatherListData(from.weather),
            mapToWindData(from.wind),
            from.timezone
        )
    }


    fun mapFromCoordData(coord: Coord): ir.omidtaheri.domain.entity.currentEntity.Coord {

        return ir.omidtaheri.domain.entity.currentEntity.Coord(coord.lat, coord.lon)
    }


    fun mapFromMainData(main: Main): ir.omidtaheri.domain.entity.currentEntity.Main {
        return ir.omidtaheri.domain.entity.currentEntity.Main(
            main.feels_like,
            main.humidity,
            main.pressure,
            main.temp,
            main.temp_max,
            main.temp_min
        )
    }

    fun mapFromWeatherListData(weather: List<Weather>): List<ir.omidtaheri.domain.entity.currentEntity.Weather> {

        return weather.map {
            ir.omidtaheri.domain.entity.currentEntity.Weather(
                it.description,
                it.icon,
                it.id,
                it.main
            )
        }
    }

    fun mapFromWindData(wind: Wind): ir.omidtaheri.domain.entity.currentEntity.Wind {
        return ir.omidtaheri.domain.entity.currentEntity.Wind(wind.speed)
    }

//////////////////

    fun mapToCoordData(coord: ir.omidtaheri.domain.entity.currentEntity.Coord): Coord {

        return Coord(coord.lat, coord.lon)
    }


    fun mapToMainData(main: ir.omidtaheri.domain.entity.currentEntity.Main): Main {
        return Main(
            main.feels_like,
            main.humidity,
            main.pressure,
            main.temp,
            main.temp_max,
            main.temp_min
        )
    }

    fun mapToWeatherListData(weather: List<ir.omidtaheri.domain.entity.currentEntity.Weather>): List<Weather> {

        return weather.map {
            Weather(
                it.description,
                it.icon,
                it.id,
                it.main
            )
        }
    }

    fun mapToWindData(wind: ir.omidtaheri.domain.entity.currentEntity.Wind): Wind {
        return Wind(wind.speed)
    }
}
