package com.devansh.contextualcards.model

import com.google.gson.annotations.SerializedName

data class CardGroup(
    val id: Long,
    val name: String,
    @SerializedName("design_type")
    val designType: DesignType,
    @SerializedName("card_type")
    val cardType: Long,
    @SerializedName("cards")
    val cardList: List<Card>,
    @SerializedName("is_scrollable")
    val isScrollable: Boolean
) {

    enum class DesignType {
        @SerializedName("HC1")
        SMALL_DISPLAY_CARD,

        @SerializedName("HC3")
        BIG_DISPLAY_CARD,

        @SerializedName("HC4")
        CENTER_CARD,

        @SerializedName("HC5")
        IMAGE_CARD,

        @SerializedName("HC6")
        SMALL_CARD_WITH_ARROW
    }
}