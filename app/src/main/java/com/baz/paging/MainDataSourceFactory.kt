package com.baz.paging

import androidx.paging.DataSource

internal class MainDataSourceFactory : DataSource.Factory<Int, Movie>() {

    override fun create(): DataSource<Int, Movie> {
        return MainDataSource()
    }
}