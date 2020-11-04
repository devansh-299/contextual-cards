package com.devansh.contextualcards.util

import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class ImageHelper {

    companion object {

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