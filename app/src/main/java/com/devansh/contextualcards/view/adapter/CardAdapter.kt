package com.devansh.contextualcards.view.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devansh.contextualcards.R
import com.devansh.contextualcards.model.Card
import com.devansh.contextualcards.model.CardGroup
import com.devansh.contextualcards.util.ImageHelper
import com.devansh.contextualcards.util.TextFormatter
import kotlinx.android.synthetic.main.item_big_display_card.view.*
import kotlinx.android.synthetic.main.item_center_card.view.*
import kotlinx.android.synthetic.main.item_image_card.view.ic_card_view
import kotlinx.android.synthetic.main.item_small_card_arrow.view.*
import kotlinx.android.synthetic.main.item_small_display_card.view.*
import kotlinx.android.synthetic.main.menu_long_press.view.*


@Suppress("DEPRECATION")
class CardAdapter(private val designType: CardGroup.DesignType)
    : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cardData: ArrayList<Card> = ArrayList()
    private val SHOW_MENU = 1
    private val HIDE_MENU = 2

    override fun getItemViewType(position: Int): Int {
        return if (cardData[position].swipeMenu) {
            SHOW_MENU
        } else {
            HIDE_MENU
        }
    }

    override fun getItemCount(): Int {
        return cardData.size
    }

    private fun showMenu(position: Int) {
        if (cardData.isNotEmpty() && !cardData[0].swipeMenu) {
            val menuCard = Card("menu_card")
            menuCard.swipeMenu = true
            cardData.add(position, menuCard)
            notifyDataSetChanged()
        }
    }

    private fun hideMenu() {
        if (cardData.isNotEmpty() && cardData[0].swipeMenu) {
            cardData.removeAt(0)
            notifyDataSetChanged()
        }
    }

    private fun deleteCard(position: Int) {
        if (cardData.size > position) {
            cardData.removeAt(position)
            notifyDataSetChanged()
        }
        hideMenu()
    }

    fun setCardData(cards: ArrayList<Card>) {
        cardData.clear()
        cardData.addAll(cards)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        // default layout selection
        var resourceLayout = R.layout.item_big_display_card
        when (designType) {
            CardGroup.DesignType.BIG_DISPLAY_CARD -> {
                resourceLayout = if (viewType == SHOW_MENU) {
                    R.layout.menu_long_press
                } else {
                    R.layout.item_big_display_card
                }
            }

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

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        when (designType) {

            CardGroup.DesignType.BIG_DISPLAY_CARD -> {
                if (!cardData[position].swipeMenu) {
                    holder.bindBigDisplayCard(cardData[position])
                    holder.itemView.setOnLongClickListener {
                        showMenu(position)
                        true
                    }
                } else {

                    holder.itemView.menu_ll_dismiss.setOnClickListener {
                        deleteCard(position + 1)
                    }

                    holder.itemView.menu_ll_remind.setOnClickListener {
                        deleteCard(position + 1)
                    }
                }
            }

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