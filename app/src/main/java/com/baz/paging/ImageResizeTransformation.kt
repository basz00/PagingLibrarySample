package com.baz.paging

import android.content.res.Resources
import android.graphics.Bitmap
import com.squareup.picasso.Transformation

internal class ImageResizeTransformation : Transformation {

    private companion object {

        private const val TRANSFORM_KEY_RESIZE = "resize()"
    }

    override fun transform(source: Bitmap): Bitmap {
        val maxPixelSize = Resources.getSystem().displayMetrics.widthPixels * 0.6
        var pixelSize = source.width.toDouble()
        var resizedWidth = source.width.toDouble()
        var resizedHeight = source.height.toDouble()
        while (pixelSize > maxPixelSize) {
            resizedWidth *= 0.75
            resizedHeight *= 0.75
            pixelSize = resizedWidth
        }
        val result = Bitmap.createScaledBitmap(source, resizedWidth.toInt(),
            resizedHeight.toInt(), true)
        if (result != source) {
            source.recycle()
        }
        return result
    }

    override fun key(): String {
        return TRANSFORM_KEY_RESIZE
    }
}