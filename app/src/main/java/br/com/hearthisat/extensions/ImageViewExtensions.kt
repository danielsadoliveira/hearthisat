package br.com.hearthisat.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Patterns
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File

/**
 * Created by danielsdo
 */

fun buildOptions(placeholder: Int? = null,
                 error: Int? = null,
                 multiTransformation: MultiTransformation<Bitmap>? = null) : RequestOptions {

    return RequestOptions().apply {
        if(placeholder != null) {
            placeholder(placeholder)
        }

        if(error != null) {
            error(error)
        }

        if (multiTransformation != null){
            transform(multiTransformation)
        }

        diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        skipMemoryCache(false)
    }
}

fun ImageView.load(uri: Uri, options: RequestOptions? = null) {
    Glide.with(context)
            .load(uri)
            .let {
                if (options != null) {
                    it.apply(options)
                } else {
                    it
                }
            }
            .into(this)
}

fun ImageView.load(url: String, options: RequestOptions? = null) {
    if(Patterns.WEB_URL.matcher(url).matches()){
        Glide.with(context)
                .load(url)
                .let {
                    if (options != null) {
                        it.apply(options)
                    } else {
                        it
                    }
                }
                .into(this)
    } else {
        val imageBytes = Base64.decode(url?.removePrefix("data:image/jpeg;base64,"), Base64.NO_WRAP)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        Glide.with(context)
                .load(decodedImage)
                .let {
                    if (options != null) {
                        it.apply(options)
                    } else {
                        it
                    }
                }
                .into(this)
    }
}

fun ImageView.load(id: Int, options: RequestOptions? = null) {
    Glide.with(context)
            .load(id)
            .let {
                if (options != null) {
                    it.apply(options)
                } else {
                    it
                }
            }
            .into(this)
}

fun ImageView.load(file: File, options: RequestOptions? = null) {
    Glide.with(context)
            .load(file)
            .let {
                if (options != null) {
                    it.apply(options)
                } else {
                    it
                }
            }
            .into(this)
}
