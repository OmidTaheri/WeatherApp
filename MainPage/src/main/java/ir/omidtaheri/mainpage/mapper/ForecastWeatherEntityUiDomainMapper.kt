package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.forecastEntity.ForecastWeatherDomainEntity
import ir.omidtaheri.mainpage.entity.forecastEntity.*
import javax.inject.Inject

class ForecastWeatherEntityUiDomainMapper @Inject constructor() :
    UiDomainMapper<ForecastWeatherUiEntity, ForecastWeatherDomainEntity> {

    override fun mapFromUiEntity(from: ForecastWeatherUiEntity): ForecastWeatherDomainEntity {
        return ForecastWeatherDomainEntity(
            mapFromCityUi(from.city),
            mapFromForecastListUi(from.list)
        )

    }

    override fun mapToUiEntity(from: ForecastWeatherDomainEntity): ForecastWeatherUiEntity {
        return ForecastWeatherUiEntity(mapToCityUi(from.city), mapToForecastListUi(from.list))
    }


    fun mapFromCityUi(city: City): ir.omidtaheri.domain.entity.forecastEntity.City {

        return ir.omidtaheri.domain.entity.forecastEntity.City(
            mapFromCoordUi(city.coord),
            city.name,
            city.timezone
        )
    }

    fun mapFromCoordUi(coord: Coord): ir.omidtaheri.domain.entity.forecastEntity.Coord {

        return ir.omidtaheri.domain.entity.forecastEntity.Coord(coord.lat, coord.lon)
    }

    fun mapFromForecastListUi(forecastList: List<ForecastList>): List<ir.omidtaheri.domain.entity.forecastEntity.ForecastList> {

        return forecastList.map {
            ir.omidtaheri.domain.entity.forecastEntity.ForecastList(
                it.dt,
                it.dt_txt,
                mapFromMainUi(it.main),
                mapFromWeatherListUi(it.weather),
                mapFromWindUi(it.wind)
            )
        }
    }

    fun mapFromWindUi(wind: Wind): ir.omidtaheri.domain.entity.forecastEntity.Wind {
        return ir.omidtaheri.domain.entity.forecastEntity.Wind(wind.speed)
    }

    fun mapFromMainUi(main: Main): ir.omidtaheri.domain.entity.forecastEntity.Main {
        return ir.omidtaheri.domain.entity.forecastEntity.Main(
            main.feels_like,
            main.humidity,
            main.pressure,
            main.temp,
            main.temp_max,
            main.temp_min
        )
    }

    fun mapFromWeatherListUi(weather: List<Weather>): List<ir.omidtaheri.domain.entity.forecastEntity.Weather> {

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


    fun mapToCityUi(city: ir.omidtaheri.domain.entity.forecastEntity.City): City {

        return City(
            mapToCoordUi(city.coord),
            city.name,
            city.timezone
        )
    }

    fun mapToCoordUi(coord: ir.omidtaheri.domain.entity.forecastEntity.Coord): Coord {

        return Coord(coord.lat, coord.lon)
    }

    fun mapToForecastListUi(forecastList: List<ir.omidtaheri.domain.entity.forecastEntity.ForecastList>): List<ForecastList> {

        return forecastList.map {
            ForecastList(
                it.dt,
                it.dt_txt,
                mapToMainUi(it.main),
                mapToWeatherListUi(it.weather),
                mapToWindUi(it.wind)
            )
        }
    }

    fun mapToWindUi(wind: ir.omidtaheri.domain.entity.forecastEntity.Wind): Wind {
        return Wind(wind.speed)
    }

    fun mapToMainUi(main: ir.omidtaheri.domain.entity.forecastEntity.Main): Main {
        return Main(
            main.feels_like,
            main.humidity,
            main.pressure,
            main.temp,
            main.temp_max,
            main.temp_min
        )
    }

    fun mapToWeatherListUi(weather: List<ir.omidtaheri.domain.entity.forecastEntity.Weather>): List<Weather> {

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
