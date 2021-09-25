package ir.omidtaheri.mainpage.mapper

import ir.omidtaheri.domain.entity.searchLoacation.Feature
import ir.omidtaheri.domain.entity.searchLoacation.Geometry
import ir.omidtaheri.domain.entity.searchLoacation.SearchLocationDomainEntity
import ir.omidtaheri.mainpage.entity.LocationEntity.LocationUiEntity
import javax.inject.Inject

class LocationEntityUiDomainMapper @Inject constructor() :
    UiDomainMapper<List<LocationUiEntity>, SearchLocationDomainEntity> {
    override fun mapFromUiEntity(from: List<LocationUiEntity>): SearchLocationDomainEntity {

        val featureList = arrayListOf<Feature>()

        from.forEach {
            featureList.add(Feature(Geometry(listOf(it.log, it.lat)), it.fullName, it.shortName))
        }

        return SearchLocationDomainEntity(featureList)
    }

    override fun mapToUiEntity(from: SearchLocationDomainEntity): List<LocationUiEntity> {

        val uiLocationList = arrayListOf<LocationUiEntity>()


        from.features.forEach {

            uiLocationList.add(
                LocationUiEntity(
                    it.geometry.coordinates[1],
                    it.geometry.coordinates[0],
                    it.text_en,
                    it.place_name_en
                )
            )
        }

        return uiLocationList

    }


}
