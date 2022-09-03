package com.example.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL

object ImageLoader {

    fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .into(imageView)
    }

    fun loadImageDrawable(imageView: ImageView, drawable: Int) {
        Glide.with(imageView.context)
            .load(drawable)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .into(imageView)
    }
}