package com.baz.paging

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MainApi {

    @GET("movie/now_playing")
    fun nowPlaying(@Query("page") page: Int): Call<MovieResponse>
}