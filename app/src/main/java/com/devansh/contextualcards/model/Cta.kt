package com.devansh.contextualcards.model

import com.google.gson.annotations.SerializedName

data class Cta(
    val text: String,
    @SerializedName("bg_color")
    val bgColor: String? = null,
    @SerializedName("other_url")
    val otherUrl: String? = null,
    @SerializedName("text_color")
    val textColor: String? = null,
    @SerializedName("url_choice")
    val urlChoice: String? = null,
    val url: String? = null
)
