package com.devansh.contextualcards.model

import com.google.gson.annotations.SerializedName

data class CardImage(
    @SerializedName("image_type")
    val imageType: String,
    @SerializedName("asset_type")
    val assetType: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null
)