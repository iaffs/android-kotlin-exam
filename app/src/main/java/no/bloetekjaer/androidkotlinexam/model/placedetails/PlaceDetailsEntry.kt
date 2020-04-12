package no.bloetekjaer.androidkotlinexam.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlaceDetailsEntry : Serializable {

    @SerializedName("place")
    @Expose
    var placeDetails: PlaceDetails? = null

}