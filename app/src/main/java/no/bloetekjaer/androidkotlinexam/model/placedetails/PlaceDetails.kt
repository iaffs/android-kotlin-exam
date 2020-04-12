package no.bloetekjaer.androidkotlinexam.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlaceDetails : Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lon")
    @Expose
    var lon: Double? = null

    @SerializedName("comments")
    @Expose
    var comments: String? = null

    @SerializedName("banner")
    @Expose
    var banner: String? = null

}