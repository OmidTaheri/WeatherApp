package ir.omidtaheri.data.mapper

import ir.omidtaheri.data.entity.searchLoacation.Feature
import ir.omidtaheri.data.entity.searchLoacation.Geometry
import ir.omidtaheri.data.entity.searchLoacation.searchLocationDataEntity
import ir.omidtaheri.domain.entity.searchLoacation.SearchLocationDomainEntity
import javax.inject.Inject

class LocationEntityDomainDataMapper @Inject constructor() :
    DomainDataMapper<searchLocationDataEntity, SearchLocationDomainEntity> {

    override fun mapFromDataEntity(from: searchLocationDataEntity): SearchLocationDomainEntity {

        return SearchLocationDomainEntity(mapFromFeatureListData(from.features))
    }

    override fun mapToDataEntity(from: SearchLocationDomainEntity): searchLocationDataEntity {
        return searchLocationDataEntity(mapToFeatureListData(from.features))
    }


    fun mapFromFeatureListData(from: List<Feature>): List<ir.omidtaheri.domain.entity.searchLoacation.Feature> {

        return from.map {
            mapFromFeatureData(it)
        }
    }


    fun mapFromFeatureData(from: Feature): ir.omidtaheri.domain.entity.searchLoacation.Feature {

        return ir.omidtaheri.domain.entity.searchLoacation.Feature(
            mapFromGeometryData(from.geometry),
            from.place_name_en,
            from.text_en
        )
    }


    fun mapFromGeometryData(from: Geometry): ir.omidtaheri.domain.entity.searchLoacation.Geometry {

        return ir.omidtaheri.domain.entity.searchLoacation.Geometry(from.coordinates)
    }

    /////////////
    fun mapToFeatureListData(from: List<ir.omidtaheri.domain.entity.searchLoacation.Feature>): List<Feature> {

        return from.map {
            mapToFeatureData(it)
        }
    }


    fun mapToFeatureData(from: ir.omidtaheri.domain.entity.searchLoacation.Feature): Feature {

        return Feature(
            mapToGeometryData(from.geometry),
            from.place_name_en,
            from.text_en
        )
    }


    fun mapToGeometryData(from: ir.omidtaheri.domain.entity.searchLoacation.Geometry): Geometry {

        return Geometry(from.coordinates)
    }
}
