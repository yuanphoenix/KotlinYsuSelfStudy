package com.example.ysuselfstudy.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.util.*

/**
 * @author  Ahyer
 * @date  2020/4/9 14:53
 * @version 1.0
 */

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context).load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view);
    }
}

//@BindingAdapter("time")
//fun bindTextSetTime(view: TextView,src:String)
//{
//    val calendar = Calendar.getInstance()
//    val hours = calendar[Calendar.HOUR_OF_DAY]
//    val minutes = calendar[Calendar.MINUTE]
//    val temp ="${hours}:${minutes}-${hours}:${minutes}"
//        //String.format("%02d:%02d-%02d:%02d", hours, minutes, hours, minutes)
//    view.setText(temp)
//}