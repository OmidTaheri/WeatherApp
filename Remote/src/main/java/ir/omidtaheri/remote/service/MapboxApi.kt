package ir.omidtaheri.remote.service

import io.reactivex.Single
import ir.omidtaheri.remote.entity.response.searchLoacation.SearchLocationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapboxApi {

    @GET("{query}.json")
    fun SearchLocationByName(
        @Path("query") city_name: String,
        @Query("types") locationType: String = "place",
        @Query("language") language: String = "en"
    ): Single<SearchLocationResponse>


}
