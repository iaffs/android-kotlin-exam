package no.bloetekjaer.androidkotlinexam.api


import no.bloetekjaer.model.placedetails.PlaceDetailsEntry
import no.bloetekjaer.model.places.PlacesEntry
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("places")
    fun getAllPlaces() : Call<PlacesEntry>

    @GET("place")
    fun getPlaceDetails(@Query("id") format: Long) : Call<PlaceDetailsEntry>
}