package com.baz.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.LazyThreadSafetyMode.NONE

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy(NONE) { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nowPlayingRecyclerView.layoutManager = GridLayoutManager(this, 3)
        nowPlayingRecyclerView.setHasFixedSize(true)
        nowPlayingRecyclerView.adapter = adapter
        viewModel.moviesLiveData.observe(this) { adapter.submitList(it) }
    }
}
