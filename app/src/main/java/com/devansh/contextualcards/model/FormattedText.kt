package com.devansh.contextualcards.model

import com.google.gson.annotations.SerializedName

data class FormattedText(
    val text: String,
    @SerializedName("entities")
    val entityList: List<Entity>
)
