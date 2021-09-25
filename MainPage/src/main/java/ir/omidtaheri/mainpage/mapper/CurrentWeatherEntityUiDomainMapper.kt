package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.currentEntity.currentWeatherDomainEntity
import ir.omidtaheri.mainpage.entity.currentEntity.*
import javax.inject.Inject

class CurrentWeatherEntityUiDomainMapper @Inject constructor() :
    UiDomainMapper<CurrentWeatherUiEntity, currentWeatherDomainEntity> {
    override fun mapFromUiEntity(from: CurrentWeatherUiEntity): currentWeatherDomainEntity {
        return currentWeatherDomainEntity(
            mapFromCoordUi(from.coord),
            mapFromMainUi(from.main),
            from.name,
            mapFromWeatherListUi(from.weather),
            mapFromWindUi(from.wind),
            from.timezone
        )
    }

    override fun mapToUiEntity(from: currentWeatherDomainEntity): CurrentWeatherUiEntity {
        return CurrentWeatherUiEntity(
            mapToCoordUi(from.coord),
            mapToMainUi(from.main),
            from.name,
            mapToWeatherListUi(from.weather),
            mapToWindUi(from.wind),
            from.timezone
        )
    }



    fun mapFromCoordUi(coord: Coord): ir.omidtaheri.domain.entity.currentEntity.Coord {

        return ir.omidtaheri.domain.entity.currentEntity.Coord(coord.lat, coord.lon)
    }


    fun mapFromMainUi(main: Main): ir.omidtaheri.domain.entity.currentEntity.Main {
        return ir.omidtaheri.domain.entity.currentEntity.Main(
            main.feels_like,
            main.humidity,
            main.pressure,
            main.temp,
            main.temp_max,
            main.temp_min
        )
    }

    fun mapFromWeatherListUi(weather: List<Weather>): List<ir.omidtaheri.domain.entity.currentEntity.Weather> {

        return weather.map {
            ir.omidtaheri.domain.entity.currentEntity.Weather(
                it.description,
                it.icon,
                it.id,
                it.main
            )
        }
    }

    fun mapFromWindUi(wind: Wind): ir.omidtaheri.domain.entity.currentEntity.Wind {
        return ir.omidtaheri.domain.entity.currentEntity.Wind(wind.speed)
    }

//////////////////

    fun mapToCoordUi(coord: ir.omidtaheri.domain.entity.currentEntity.Coord): Coord {

        return Coord(coord.lat, coord.lon)
    }


    fun mapToMainUi(main: ir.omidtaheri.domain.entity.currentEntity.Main): Main {
        return Main(
            main.feels_like,
            main.humidity,
            main.pressure,
            main.temp,
            main.temp_max,
            main.temp_min
        )
    }

    fun mapToWeatherListUi(weather: List<ir.omidtaheri.domain.entity.currentEntity.Weather>): List<Weather> {

        return weather.map {
            Weather(
                it.description,
                it.icon,
                it.id,
                it.main
            )
        }
    }

    fun mapToWindUi(wind: ir.omidtaheri.domain.entity.currentEntity.Wind): Wind {
        return Wind(wind.speed)
    }


}
