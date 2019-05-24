package com.baz.paging

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import java.util.concurrent.Executors
import kotlin.LazyThreadSafetyMode.NONE

internal class MainViewModel : ViewModel() {

    private val executor = Executors.newFixedThreadPool(5)
    private val mainDataSourceFactory = MainDataSourceFactory()
    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(10)
        .setPageSize(20).build()

    internal val moviesLiveData by lazy(NONE) {
        LivePagedListBuilder(mainDataSourceFactory, pagedListConfig)
            .setFetchExecutor(executor)
            .build()
    }

}