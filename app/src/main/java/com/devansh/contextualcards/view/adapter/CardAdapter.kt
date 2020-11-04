package com.devansh.contextualcards.view.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devansh.contextualcards.R
import com.devansh.contextualcards.util.TextFormatter
import com.devansh.contextualcards.model.Card
import com.devansh.contextualcards.model.CardGroup
import com.devansh.contextualcards.util.ImageHelper
import kotlinx.android.synthetic.main.item_big_display_card.view.*
import kotlinx.android.synthetic.main.item_center_card.view.*
import kotlinx.android.synthetic.main.item_image_card.view.*
import kotlinx.android.synthetic.main.item_small_card_arrow.view.*
import kotlinx.android.synthetic.main.item_small_display_card.view.*


@Suppress("DEPRECATION")
class CardAdapter(private val designType: CardGroup.DesignType)
    : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cardData: ArrayList<Card> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        // default layout selection
        var resourceLayout = R.layout.item_big_display_card
        when (designType) {
            CardGroup.DesignType.SMALL_CARD_WITH_ARROW ->
                resourceLayout = R.layout.item_small_card_arrow

            CardGroup.DesignType.IMAGE_CARD ->
                resourceLayout = R.layout.item_image_card

            CardGroup.DesignType.CENTER_CARD ->
                resourceLayout = R.layout.item_center_card

            CardGroup.DesignType.SMALL_DISPLAY_CARD ->
                resourceLayout = R.layout.item_small_display_card
        }
        return CardViewHolder(
            LayoutInflater.from(parent.context).inflate(resourceLayout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cardData.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        when (designType) {
            CardGroup.DesignType.BIG_DISPLAY_CARD ->
                holder.bindBigDisplayCard(cardData[position])

            CardGroup.DesignType.SMALL_CARD_WITH_ARROW ->
                holder.bindSmallCardArrow(cardData[position])

            CardGroup.DesignType.IMAGE_CARD ->
                holder.bindImageCard(cardData[position])

            CardGroup.DesignType.CENTER_CARD ->
                holder.bindCenterCard(cardData[position])

            CardGroup.DesignType.SMALL_DISPLAY_CARD ->
                holder.bindSmallCard(cardData[position])
        }
    }

    fun setCardData(cards: ArrayList<Card>) {
        cardData.clear()
        cardData.addAll(cards)
        notifyDataSetChanged()
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindBigDisplayCard(card: Card) {
            TextFormatter.applyFormattedText(
                card.formattedTitle, itemView.bdc_tv_title, card.title)

            TextFormatter.applyFormattedText(
                card.formattedDescription, itemView.bdc_tv_description, card.description)

            card.bgImage?.imageUrl?.let {
                ImageHelper.loadImage(it, itemView, itemView.bdc_card_view)
            }
        }

        fun bindSmallCardArrow(card: Card) {
            TextFormatter.applyFormattedText(
                card.formattedTitle, itemView.sca_tv_title, card.title)

            card.icon?.imageUrl?.let {
                ImageHelper.loadImage(it, itemView, itemView.sca_iv_icon)
            }
        }

        fun bindImageCard(card: Card) {
            card.bgImage?.imageUrl?.let {
                ImageHelper.loadImage(it, itemView, itemView.ic_card_view)
            }
        }

        fun bindCenterCard(card: Card) {
            TextFormatter.applyFormattedText(
                card.formattedTitle, itemView.cc_tv_title, card.title)

            TextFormatter.applyFormattedText(
                card.formattedDescription, itemView.cc_tv_description, card.description)

            val gradient = GradientDrawable()
            gradient.colors = card.bgGradient?.colorList?.map { Color.parseColor(it) }?.toIntArray()
            gradient.shape = GradientDrawable.RECTANGLE
            itemView.cc_linear_layout.setBackgroundDrawable(gradient)

            card.icon?.imageUrl?.let {
                ImageHelper.loadImage(it, itemView, itemView.cc_iv_icon)
            }
        }

        fun bindSmallCard(card: Card) {
            TextFormatter.applyFormattedText(
                card.formattedTitle, itemView.sdc_tv_title, card.title)

            TextFormatter.applyFormattedText(
                card.formattedDescription, itemView.sdc_tv_description, card.description)

            itemView.sdc_linear_layout.setBackgroundColor(Color.parseColor(card.bgColor))

            card.icon?.imageUrl?.let {
                ImageHelper.loadImage(it, itemView, itemView.sdc_iv_icon)
            }
        }
    }
}