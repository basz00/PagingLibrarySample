package com.baz.paging

internal class ImageUrlHelper {

    private companion object {

        private const val BASE_URL = "https://image.tmdb.org/t/p/"
        private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%s/0.jpg"
        private const val PORTRAIT_WIDTH_CODE = "w185"
        private const val BANNER_WIDTH_CODE = "w342"
    }

    fun generatePosterImageUrl(imageUrl: String) = "$BASE_URL$PORTRAIT_WIDTH_CODE$imageUrl"

    fun generateBannerImageUrl(imageUrl: String) = "$BASE_URL$BANNER_WIDTH_CODE$imageUrl"

    fun generateYoutubeThumbnailUrl(videoId: String) = String.format(YOUTUBE_THUMBNAIL_URL, videoId)
}