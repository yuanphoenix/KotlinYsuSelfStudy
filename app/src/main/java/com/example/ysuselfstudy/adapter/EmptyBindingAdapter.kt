package com.example.ysuselfstudy.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.ysuselfstudy.R
import java.util.*

/**
 * @author  Ahyer
 * @date  2020/4/9 14:53
 * @version 1.0
 * 这是一个顶层方法文件
 */

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context).load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.ic_error)
            .into(view);
    }
}