package no.bloetekjaer.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Properties of a distinct Place
class Property(
    @SerializedName("id")
    @Expose var id: Long?,

    @SerializedName("name")
    @Expose var name: String?
) : Serializable

