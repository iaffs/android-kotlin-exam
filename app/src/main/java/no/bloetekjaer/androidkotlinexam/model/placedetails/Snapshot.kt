package no.bloetekjaer.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Snapshot : Serializable {
    @SerializedName("time")
    @Expose
    var time: String? = null

    @SerializedName("reason")
    @Expose
    var reason: String? = null

}