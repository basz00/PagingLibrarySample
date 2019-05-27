package com.baz.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.paging.PageKeyedDataSource

internal class MainDataSource : PageKeyedDataSource<Int, Movie>() {

    private val mainRepository = MainRepository()

    internal val movieStateLiveData = MutableLiveData<MovieState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        mainRepository.getMovies(1).map {
            when (it) {
                is MovieResult.Success -> callback.onResult(it.movies, null, 2)
                else -> consumeErrorOrProgressResult(it)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        mainRepository.getMovies(params.key).map {
            when (it) {
                is MovieResult.Success -> callback.onResult(it.movies, params.key + 1)
                else -> consumeErrorOrProgressResult(it)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

    private fun consumeErrorOrProgressResult(movieResult: MovieResult) {
        when (movieResult) {
            is MovieResult.Error -> movieStateLiveData.value = MovieState.Error(movieResult.error)
            is MovieResult.Progress -> movieStateLiveData.value = MovieState.Progress(movieResult.loading)
        }
    }
}