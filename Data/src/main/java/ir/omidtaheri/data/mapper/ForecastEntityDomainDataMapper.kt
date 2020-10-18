package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.forecastEntity.*
import ir.omidtaheri.domain.entity.forecastEntity.forecastWeatherDomainEntity
import javax.inject.Inject

class ForecastEntityDomainDataMapper @Inject constructor() :
    DomainDataMapper<forecastWeatherDataEntity, forecastWeatherDomainEntity> {

    override fun mapFromDataEntity(from: forecastWeatherDataEntity): forecastWeatherDomainEntity {

        return forecastWeatherDomainEntity(
            mapFromCityData(from.city),
            mapFromForecastListData(from.list)
        )
    }

    override fun mapToDataEntity(from: forecastWeatherDomainEntity): forecastWeatherDataEntity {

        return forecastWeatherDataEntity(mapToCityData(from.city), mapToForecastListData(from.list))
    }


    fun mapFromCityData(city: City): ir.omidtaheri.domain.entity.forecastEntity.City {

        return ir.omidtaheri.domain.entity.forecastEntity.City(
            mapFromCoordData(city.coord),
            city.name,
            city.timezone
        )
    }

    fun mapFromCoordData(coord: Coord): ir.omidtaheri.domain.entity.forecastEntity.Coord {

        return ir.omidtaheri.domain.entity.forecastEntity.Coord(coord.lat, coord.lon)
    }

    fun mapFromForecastListData(forecastList: List<forecastList>): List<ir.omidtaheri.domain.entity.forecastEntity.forecastList> {

        return forecastList.map {
            ir.omidtaheri.domain.entity.forecastEntity.forecastList(
                it.dt,
                it.dt_txt,
                mapFromMainData(it.main),
                mapFromWeatherListData(it.weather),
                mapFromWindData(it.wind)
            )
        }
    }

    fun mapFromWindData(wind: Wind): ir.omidtaheri.domain.entity.forecastEntity.Wind {
        return ir.omidtaheri.domain.entity.forecastEntity.Wind(wind.speed)
    }

    fun mapFromMainData(main: Main): ir.omidtaheri.domain.entity.forecastEntity.Main {
        return ir.omidtaheri.domain.entity.forecastEntity.Main(
            main.feels_like,
            main.humidity,
            main.pressure,
            main.temp,
            main.temp_max,
            main.temp_min
        )
    }

    fun mapFromWeatherListData(weather: List<Weather>): List<ir.omidtaheri.domain.entity.forecastEntity.Weather> {

        return weather.map {
            ir.omidtaheri.domain.entity.forecastEntity.Weather(
                it.description,
                it.icon,
                it.id,
                it.main
            )
        }
    }

    ////////////////////


    fun mapToCityData(city: ir.omidtaheri.domain.entity.forecastEntity.City): City {

        return City(
            mapToCoordData(city.coord),
            city.name,
            city.timezone
        )
    }

    fun mapToCoordData(coord: ir.omidtaheri.domain.entity.forecastEntity.Coord): Coord {

        return Coord(coord.lat, coord.lon)
    }

    fun mapToForecastListData(forecastList: List<ir.omidtaheri.domain.entity.forecastEntity.forecastList>): List<forecastList> {

        return forecastList.map {
            forecastList(
                it.dt,
                it.dt_txt,
                mapToMainData(it.main),
                mapToWeatherListData(it.weather),
                mapToWindData(it.wind)
            )
        }
    }

    fun mapToWindData(wind: ir.omidtaheri.domain.entity.forecastEntity.Wind): Wind {
        return Wind(wind.speed)
    }

    fun mapToMainData(main: ir.omidtaheri.domain.entity.forecastEntity.Main): Main {
        return Main(
            main.feels_like,
            main.humidity,
            main.pressure,
            main.temp,
            main.temp_max,
            main.temp_min
        )
    }

    fun mapToWeatherListData(weather: List<ir.omidtaheri.domain.entity.forecastEntity.Weather>): List<Weather> {

        return weather.map {
            Weather(
                it.description,
                it.icon,
                it.id,
                it.main
            )
        }
    }
}
