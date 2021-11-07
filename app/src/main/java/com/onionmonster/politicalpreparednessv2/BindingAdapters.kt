package com.onionmonster.politicalpreparednessv2

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("twitterUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    val TAG = "Dev/BindingAdapter"
//    val imgUrl = "https://pbs.twimg.com/profile_images/839166780165611520/v_lrshK__normal.jpg"

    Log.d(TAG, "Inside BindingAdapter: " + imgUrl.toString())

    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}