package com.devansh.contextualcards.model

import com.google.gson.annotations.SerializedName

data class Gradient(
    val angle: Int,
    @SerializedName("colors")
    val colorList: List<String>
)
