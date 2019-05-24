package com.baz.paging

import androidx.paging.PageKeyedDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MainDataSource : PageKeyedDataSource<Int, Movie>() {

    private val restApiFactory = RestApiFactory.create()
    private val mainApi = restApiFactory.create(MainApi::class.java)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        mainApi.nowPlaying(1).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.movies?.let {
                        callback.onResult(it, null, 2)
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        mainApi.nowPlaying(params.key).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val nextKey = if (params.key == response.body()?.totalPages) null else params.key + 1
                    response.body()?.movies?.let {
                        callback.onResult(it, nextKey)
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}
}