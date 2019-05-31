package com.baz.paging

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MainApi {

    @GET("movie/now_playing")
    fun nowPlaying(@Query("page") page: Int): Deferred<MovieResponse>
}