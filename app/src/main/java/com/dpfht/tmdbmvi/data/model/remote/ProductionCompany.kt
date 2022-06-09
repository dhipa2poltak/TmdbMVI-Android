package com.dpfht.tmdbmvi.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class ProductionCompany(
    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("origin_country")
    @Expose
    val originCountry: String? = null,

    @SerializedName("logo_path")
    @Expose
    val logoPath: String? = null,

    @SerializedName("id")
    @Expose
    val id: Int = 0,
)
