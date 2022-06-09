package com.dpfht.tmdbmvi.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class SpokenLanguage(
    @SerializedName("iso_639_1")
    @Expose
    val iso6391: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("english_name")
    @Expose
    val englishName: String? = null
)
