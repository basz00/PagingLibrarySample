package com.baz.paging

internal class MainRepository {

    private val restApiFactory = RestApiFactory.create()
    private val mainApi = restApiFactory.create(MainApi::class.java)

    suspend fun getMovies(page: Int): MovieResult {
        return try {
            val response = mainApi.nowPlaying(page).await()
            MovieResult.Success(response.movies)
        } catch (e: Exception) {
            MovieResult.Error(e.message ?: "")
        }
    }
}