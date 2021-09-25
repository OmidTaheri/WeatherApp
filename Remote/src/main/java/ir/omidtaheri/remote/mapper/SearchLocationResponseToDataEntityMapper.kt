package ir.omidtaheri.remote.mapper


import ir.omidtaheri.data.entity.searchLoacation.searchLocationDataEntity
import ir.omidtaheri.remote.entity.response.searchLoacation.Feature
import ir.omidtaheri.remote.entity.response.searchLoacation.Geometry
import ir.omidtaheri.remote.entity.response.searchLoacation.SearchLocationResponse
import javax.inject.Inject

class SearchLocationResponseToDataEntityMapper @Inject constructor() :
    ResponseToDataEntityMapper<SearchLocationResponse, searchLocationDataEntity> {

    override fun mapFromDTO(from: SearchLocationResponse): searchLocationDataEntity {

        return searchLocationDataEntity(mapFromFeatureListDTO(from.features))
    }


    fun mapFromFeatureListDTO(from: List<Feature>): List<ir.omidtaheri.data.entity.searchLoacation.Feature> {

        return from.map {
            mapFromFeatureDTO(it)
        }
    }


    fun mapFromFeatureDTO(from: Feature): ir.omidtaheri.data.entity.searchLoacation.Feature {

        return ir.omidtaheri.data.entity.searchLoacation.Feature(mapFromGeometryDTO(from.geometry),from.place_name_en,from.text_en)
    }


    fun mapFromGeometryDTO(from: Geometry): ir.omidtaheri.data.entity.searchLoacation.Geometry {

        return ir.omidtaheri.data.entity.searchLoacation.Geometry(from.coordinates)
    }
}