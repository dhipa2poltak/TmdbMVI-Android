package com.dpfht.tmdbmvi.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class Trailer(
    val id: String? = null,

    @SerializedName("iso_639_1")
    @Expose
    val iso6391: String? = null,

    @SerializedName("iso_3166_1")
    @Expose
    val iso31661: String? = null,

    val key: String? = null,
    val name: String? = null,
    val site: String? = null,
    val size: Int = 0,
    val type: String? = null
)
