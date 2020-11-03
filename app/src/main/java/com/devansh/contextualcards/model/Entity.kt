package com.devansh.contextualcards.model

import com.google.gson.annotations.SerializedName

data class Entity(
    val text: String,
    val type: String? = null,
    val color: String? = null,
    val url: String? = null,
    @SerializedName("url_choice")
    val urlChoice: String? = null,
    @SerializedName("other_url")
    val otherUrl: String? = null
)
