package com.example.ysuselfstudy.adapter

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.logic.getWeek
import jp.wasabeef.glide.transformations.BlurTransformation
import razerdp.blur.BlurImageView
import java.util.*

/**
 * @author  Ahyer
 * @date  2020/4/9 14:53
 * @version 1.0
 * 这是一个顶层方法文件
 */
private val TAG = "适配文件"

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

@BindingAdapter("imageCircle")
fun bindImageCircle(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(if (imageUrl.isNullOrEmpty()) R.mipmap.ic_qq else imageUrl)
        .apply(bitmapTransform(CircleCrop()))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view);
}


@BindingAdapter("imageBlur")
fun bindImageBlur(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(if (imageUrl.isNullOrEmpty()) R.drawable.ic_back else imageUrl)
        .apply(bitmapTransform(BlurTransformation(25, 3)))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)

}

@BindingAdapter("imageBackGround")
fun bindImageBackGroundFromUrl(view: ImageView, imageUrl: String?) {

    val pref = YsuSelfStudyApplication.context.getSharedPreferences("data", Context.MODE_PRIVATE)

    val savedPath = pref.getString("picpath", "0")
    var realPath =
        if (!imageUrl.isNullOrEmpty())
            imageUrl
        else if (savedPath == null || savedPath == "0")
            R.drawable.ic_pikaqiu
        else if (savedPath != "0")
            savedPath
        else
            R.drawable.ic_pikaqiu



    Glide.with(view.context)
        .asBitmap()
        .load(realPath)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.ic_pikaqiu)
        .into(view);

}