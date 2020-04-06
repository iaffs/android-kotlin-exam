package no.bloetekjaer.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Meta : Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null
}