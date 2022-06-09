package com.dpfht.tmdbmvi.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    @Expose
    val iso31661: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null
)
