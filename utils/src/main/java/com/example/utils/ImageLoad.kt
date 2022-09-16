package com.example.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL

object ImageLoader {

    fun loadImage(context: Context, ImageView: ImageView, url: String) {
        Glide.with(context)
            .load(url)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(ImageView);
    }


    fun loadImageDrawable(imageView: ImageView, drawable: Int) {
        Glide.with(imageView.context)
            .load(drawable)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .into(imageView)
    }

    fun loadImageDrawable(context: Context, ImageView: ImageView, drawable: Int) {
        Glide.with(context)
            .load(drawable)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .placeholder(R.drawable.place_holder_image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(ImageView);
    }

    // set custom position using margins for mapview

    fun setMargins(view: View, left: Int, top: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, 0, 0)
            view.requestLayout()
        }
    }
}