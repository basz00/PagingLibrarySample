package com.baz.paging

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baz.paging.MainAdapter.ViewHolder

internal class MainAdapter : PagedListAdapter<Movie, ViewHolder>(MovieDiffUtil()) {

    private val imageLoader = ImageLoader()
    private val imageUrlHelper = ImageUrlHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, R.layout.movie_item_list, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nowPlayingMoviePosterImageView = view.findViewById<ImageView>(R.id.nowPlayingMoviePosterImageView)
        private val nowPlayingMovieNameTextView = view.findViewById<TextView>(R.id.nowPlayingMovieNameTextView)
        private val nowPlayingMovieRatingTextView = view.findViewById<TextView>(R.id.nowPlayingMovieRatingTextView)

        fun bind(movie: Movie) {
            movie.posterPath?.let {
                imageLoader.loadImage(nowPlayingMoviePosterImageView, imageUrlHelper.generatePosterImageUrl(it))
            }
            nowPlayingMovieNameTextView.text = movie.title
            nowPlayingMovieRatingTextView.text = movie.voteAverage.toString()
        }
    }

    class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
    }
}