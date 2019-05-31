package com.baz.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class MainDataSource : PageKeyedDataSource<Int, Movie>() {

    private val mainRepository = MainRepository()

    internal val movieStateLiveData = MutableLiveData<MovieState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        CoroutineScope(Dispatchers.Main).launch {
            movieStateLiveData.value = MovieState.Progress(true)
            when (val result = mainRepository.getMovies(1)) {
                is MovieResult.Success -> {
                    movieStateLiveData.value = MovieState.Progress(false)
                    callback.onResult(result.movies, null, 2)
                }
                is MovieResult.Error -> {
                    val retry = { loadInitial(params, callback) }
                    handleError(result.error, retry)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        CoroutineScope(Dispatchers.Main).launch {
            movieStateLiveData.value = MovieState.Progress(true)
            when (val result = mainRepository.getMovies(params.key)) {
                is MovieResult.Success -> {
                    movieStateLiveData.value = MovieState.Progress(false)
                    callback.onResult(result.movies, params.key + 1)
                }
                is MovieResult.Error -> {
                    val retry = { loadAfter(params, callback) }
                    handleError(result.error, retry)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

    private fun handleError(error: String, retry: () -> Any) {
        movieStateLiveData.value = MovieState.Progress(false)
        movieStateLiveData.value = MovieState.Error(error, retry)
    }
}