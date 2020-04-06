package no.bloetekjaer.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Place object with it's attributes
// API returns a JSON array with Place objects and attributes
class Place(
    @SerializedName("properties")
    @Expose var properties: Property?, @SerializedName("geometry")
    @Expose var geometry: Geometry?
) : Serializable

