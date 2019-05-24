package com.baz.paging


import android.widget.ImageView
import com.squareup.picasso.Picasso

internal class ImageLoader {

    fun loadImage(imageView: ImageView, url: String) {
        Picasso.get()
            .load(url)
            .transform(ImageResizeTransformation())
            .into(imageView)
    }
}