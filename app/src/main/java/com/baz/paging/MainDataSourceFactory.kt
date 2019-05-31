package com.baz.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

internal class MainDataSourceFactory : DataSource.Factory<Int, Movie>() {

    private val mainDataSource = MainDataSource()

    internal val mainDataSourceLiveData = MutableLiveData<MainDataSource>()

    override fun create(): DataSource<Int, Movie> {
        mainDataSourceLiveData.postValue(mainDataSource)
        return mainDataSource
    }
}