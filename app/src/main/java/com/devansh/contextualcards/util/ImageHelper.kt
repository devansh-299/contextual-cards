package com.devansh.contextualcards.util

import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/**
 * A utility class to handle all the image related stuff done in the application.
 * This class internally uses [Glide] to handle image loading and cropping etc.
 */
class ImageHelper {

    companion object {
        /**
         * @param imageUrl
         * @param rootView [Glide] life-cycle context
         * @param loadInto view whose background is to be set
         */
        fun loadImage(imageUrl: String, rootView: View, loadInto: View) {
            Glide.with(rootView)
                .load(imageUrl)
                .into(object :
                    CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?) {
                        loadInto.background = resource
                    }
                })
        }
    }
}