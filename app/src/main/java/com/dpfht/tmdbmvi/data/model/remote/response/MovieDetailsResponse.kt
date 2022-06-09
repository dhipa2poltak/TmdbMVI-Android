package com.dpfht.tmdbmvi.data.model.remote.response

import com.dpfht.tmdbmvi.data.model.remote.Genre
import com.dpfht.tmdbmvi.data.model.remote.ProductionCompany
import com.dpfht.tmdbmvi.data.model.remote.ProductionCountry
import com.dpfht.tmdbmvi.data.model.remote.SpokenLanguage
import com.dpfht.tmdbmvi.domain.model.GetMovieDetailsResult
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class MovieDetailsResponse(

    val adult: Boolean = false,

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null,

    @SerializedName("belongs_to_collection")
    @Expose
    val belongsToCollection: Any? = null,

    val budget: Int = 0,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Int = 0,

    @SerializedName("imdb_id")
    @Expose
    val imdbId: String? = null,

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null,

    val overview: String? = null,
    val popularity: Float = 0.0f,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,

    @SerializedName("production_companies")
    @Expose
    val productionCompanies: List<ProductionCompany>? = null,

    @SerializedName("production_countries")
    @Expose
    val productionCountries: List<ProductionCountry>? = null,

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null,

    val revenue: Int = 0,
    val runtime: Int = 0,

    @SerializedName("spoken_languages")
    @Expose
    val spokenLanguages: List<SpokenLanguage>? = null,

    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean = false,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Float = 0.0f,

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0
)

fun MovieDetailsResponse.toDomain() = GetMovieDetailsResult(
    this.id, this.title ?: "", this.overview ?: "", this.posterPath ?: ""
)
