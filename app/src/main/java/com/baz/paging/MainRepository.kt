package com.baz.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MainRepository {

    private val restApiFactory = RestApiFactory.create()
    private val mainApi = restApiFactory.create(MainApi::class.java)
    private val movieResultLiveData = MutableLiveData<MovieResult>()

    fun getMovies(page: Int): LiveData<MovieResult> {
        movieResultLiveData.value = MovieResult.Progress(true)
        mainApi.nowPlaying(page).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                movieResultLiveData.value = MovieResult.Progress(false)
                if (response.isSuccessful) {
                    response.body()?.movies?.let {
                        movieResultLiveData.value = MovieResult.Success(it)
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                movieResultLiveData.value = MovieResult.Progress(false)
                movieResultLiveData.value = MovieResult.Error(t.message ?: "")
            }
        })
        return movieResultLiveData
    }
}