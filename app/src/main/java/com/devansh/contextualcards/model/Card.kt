package com.devansh.contextualcards.model

import com.google.gson.annotations.SerializedName

data class Card(
    val name: String,
    val title: String? = null,
    @SerializedName("formatted_title")
    val formattedTitle: FormattedText? = null,
    val description: String? = null,
    @SerializedName("formatted_description")
    val formattedDescription: FormattedText? = null,
    val url: String? = null,
    @SerializedName("bg_image")
    val bgImage: CardImage? = null,
    @SerializedName("bg_color")
    val bgColor: String? = null,
    @SerializedName("cta")
    val ctaList: List<Cta>? = null,
    val icon: CardImage? = null,
    @SerializedName("bg_gradient")
    val bgGradient: Gradient? = null,
    var swipeMenu: Boolean = false
)