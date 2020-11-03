package com.devansh.contextualcards.model

import com.google.gson.annotations.SerializedName

data class CardGroup(
    val id: Int,
    val name: String,
    @SerializedName("design_type")
    val designType: DesignType,
    @SerializedName("card_type")
    val cardType: String,
    @SerializedName("cards")
    val cardList: List<Card>,
    @SerializedName("is_scrollable")
    val isScrollable: Boolean
) {
    enum class DesignType(val type: String) {
        SMALL_DISPLAY_CARD("HC1"),
        BIG_DISPLAY_CARD("HC3"),
        CENTER_CARD("HC4"),
        IMAGE_CARD("HC5"),
        SMALL_CARD_WITH_ARROW("HC6")
    }
}