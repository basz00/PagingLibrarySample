package com.baz.paging

internal sealed class MovieState {

    data class Error(val error: String) : MovieState()

    data class Progress(val loading: Boolean) : MovieState()
}