package com.devansh.contextualcards.view.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.devansh.contextualcards.R
import com.devansh.contextualcards.util.TextFormatter
import com.devansh.contextualcards.model.Card
import com.devansh.contextualcards.model.CardGroup
import kotlinx.android.synthetic.main.item_big_display_card.view.*
import kotlinx.android.synthetic.main.item_image_card.view.*
import kotlinx.android.synthetic.main.item_small_card_arrow.view.*


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
            if (card.formattedTitle != null) {
                TextFormatter.applyFormattedText(
                    card.formattedTitle, itemView.bdc_tv_title, card.title)
            } else {
                itemView.bdc_tv_title.text = card.title
            }

            if (card.formattedDescription != null) {
                TextFormatter.applyFormattedText(
                    card.formattedDescription, itemView.bdc_tv_description, card.description)
            } else {
                itemView.bdc_tv_description.text = card.description
            }

            Glide.with(itemView)
                .load(card.bgImage?.imageUrl)
                .into(object :
                    CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?) {
                        itemView.bdc_card_view.background = resource
                    }
                })
        }

        fun bindSmallCardArrow(card: Card) {
            if (card.formattedTitle != null) {
                TextFormatter.applyFormattedText(
                    card.formattedTitle, itemView.sca_tv_title, card.title)
            } else {
                itemView.bdc_tv_title.text = card.title
            }

            Glide.with(itemView)
                .load(card.icon?.imageUrl)
                .into(object :
                    CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?) {
                        itemView.sca_iv_icon.background = resource
                    }
                })
        }

        fun bindImageCard(card: Card) {
            Glide.with(itemView)
                .load(card.bgImage?.imageUrl)
                .into(object :
                    CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?) {
                        itemView.ic_card_view.background = resource
                    }
                })
        }

        fun bindCenterCard(card: Card) {

        }

        fun bindSmallCard(card: Card) {

        }
    }
}