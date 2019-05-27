package com.baz.paging

internal sealed class MovieResult {

    data class Success(val movies: List<Movie>) : MovieResult()

    data class Error(val error: String) : MovieResult()

    data class Progress(val loading: Boolean) : MovieResult()
}