package com.devansh.contextualcards.view.adapter

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devansh.contextualcards.ContextualCardApplication
import com.devansh.contextualcards.R
import com.devansh.contextualcards.model.Card
import com.devansh.contextualcards.model.CardGroup
import com.devansh.contextualcards.util.ImageHelper
import com.devansh.contextualcards.util.PreferenceHelper
import com.devansh.contextualcards.util.TextFormatter
import kotlinx.android.synthetic.main.item_big_display_card.view.*
import kotlinx.android.synthetic.main.item_center_card.view.*
import kotlinx.android.synthetic.main.item_image_card.view.ic_card_view
import kotlinx.android.synthetic.main.item_small_card_arrow.view.*
import kotlinx.android.synthetic.main.item_small_display_card.view.*
import kotlinx.android.synthetic.main.menu_long_press.view.*


/**
 * The inner [RecyclerView] that used to display individual [Card] fetched from the API.
 *
 * @param designType used to specify the [CardGroup.DesignType] for current group
 * @param groupId used to specify the groupId if in case current group needs to be excluded
 * from being displayed to the user permanently.
 */
@Suppress("DEPRECATION")
class CardAdapter(
    private val designType: CardGroup.DesignType,
    private val groupId: Long
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cardData: ArrayList<Card> = ArrayList()
    private val SHOW_MENU = 1
    private val HIDE_MENU = 2

    /**
     * To specify the view type as either 'SHOW_MENU' or 'HIDE_MENU'.
     */
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

    /**
     * This method is used to display "Right-Swipe Menu" by simply adding a new card item, let's
     * call it as "Menu Card", in the list. The if conditions in the beginning are put to
     * avoid creation or multiple "Menu Cards" for the same item. For creating a "Menu Card", simply
     * an instance of [Card] is created with it's name as "menu_card" and 'swipeMenu' field set to
     * true. This field is false for all other(normal) cards.
     *
     * @param position to specify the card for which menu card is to be created
     */
    private fun showMenu(position: Int) {
        if (cardData.isNotEmpty() && !cardData[0].swipeMenu) {
            val menuCard = Card("menu_card")
            menuCard.swipeMenu = true
            cardData.add(position, menuCard)
            notifyDataSetChanged()
        }
    }

    /**
     * To hide the "Menu Card". This function is not private as it is also being
     * called inside [CardGroupAdapter].
     */
    fun hideMenu() {
        if (cardData.isNotEmpty() && cardData[0].swipeMenu) {
            cardData.removeAt(0)
            notifyDataSetChanged()
        }
    }

    /**
     * To delete the specified card from list and store it's group's id in local storage to avoid
     * putting this group in list in future.
     *
     * @param position to specify the card whose group is to be excluded
     */
    private fun deleteCard(position: Int) {
        if (cardData.size > position) {
            cardData.removeAt(position)
            notifyDataSetChanged()
            PreferenceHelper.addGroupId(groupId.toString())
        }
        hideMenu()
    }

    /**
     * To set/update the [ArrayList] of [Card].
     * @param cards updated list
     */
    fun setCardData(cards: ArrayList<Card>) {
        cardData.clear()
        cardData.addAll(cards)
        notifyDataSetChanged()
    }

    /**
     * To specify the [CardViewHolder] depending upon the [CardGroup.DesignType] and whether
     * following item is "Menu Card" or not.
     *
     * @param parent the parent [ViewGroup]
     * @param viewType used to specify whether this a "Menu Card" or not. Note that the 'viewType'
     * is 'SHOW_MENU' for a "Menu Card" and 'HIDE_MENU' for others.
     *
     */
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

    /**
     * This method is used to bind data with corresponding views. Since in our case we have
     * varied sets of data and their corresponding views, this method again makes calls to
     * different sets of methods depending upon the card type to bind the views accordingly.
     *
     * @param holder instance of [CardViewHolder] which contains methods for binding data with
     * their corresponding views.
     *
     * @param position used to specify the card whose data will be binded.
     */
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

        /**
         * To bind the data and view for 'Big Display Cards'
         *
         * @param card an instance [Card] which contains the data fetched from the APIs.
         */
        fun bindBigDisplayCard(card: Card) {
            TextFormatter.applyFormattedText(
                card.formattedTitle, itemView.bdc_tv_title, card.title
            )

            TextFormatter.applyFormattedText(
                card.formattedDescription, itemView.bdc_tv_description, card.description
            )

            card.bgImage?.imageUrl?.let {
                ImageHelper.loadImage(it, itemView, itemView.bdc_card_view)
            }

            card.ctaList?.get(0)?.let { cta ->
                itemView.bdc_bt_action.setBackgroundColor(Color.parseColor(cta.bgColor))
                itemView.bdc_bt_action.text = cta.text
                itemView.bdc_bt_action.setTextColor(Color.parseColor(cta.textColor))
                itemView.bdc_bt_action.setOnClickListener {
                    if (cta.url != null) {
                        launchAction(cta.url)
                    } else if (cta.otherUrl != null) {
                        launchAction(cta.otherUrl)
                    }
                }
            }
        }

        /**
         * To bind the data and view for 'Small Card with Arrows'
         *
         * @param card an instance [Card] which contains the data fetched from the APIs.
         */
        fun bindSmallCardArrow(card: Card) {
            TextFormatter.applyFormattedText(
                card.formattedTitle, itemView.sca_tv_title, card.title
            )

            itemView.sca_card_view.setCardBackgroundColor(Color.parseColor(card.bgColor))

            card.icon?.imageUrl?.let {
                ImageHelper.loadImage(it, itemView, itemView.sca_iv_icon)
            }
            itemView.sca_card_view.setOnClickListener {
                if (card.url != null) {
                    launchAction(card.url)
                }
            }
        }

        /**
         * To bind the data and view for 'Image Cards'
         *
         * @param card an instance [Card] which contains the data fetched from the APIs.
         */
        fun bindImageCard(card: Card) {
            card.bgImage?.imageUrl?.let {
                ImageHelper.loadImage(it, itemView, itemView.ic_card_view)
            }

            itemView.ic_card_view.setOnClickListener {
                if (card.url != null) {
                    launchAction(card.url)
                }
            }
        }

        /**
         * To bind the data and view for 'Center Cards'
         *
         * @param card an instance [Card] which contains the data fetched from the APIs.
         */
        fun bindCenterCard(card: Card) {
            TextFormatter.applyFormattedText(
                card.formattedTitle, itemView.cc_tv_title, card.title
            )

            TextFormatter.applyFormattedText(
                card.formattedDescription, itemView.cc_tv_description, card.description
            )

            val gradient = GradientDrawable()
            gradient.colors = card.bgGradient?.colorList?.map { Color.parseColor(it) }?.toIntArray()
            gradient.shape = GradientDrawable.RECTANGLE
            itemView.cc_linear_layout.setBackgroundDrawable(gradient)

            card.icon?.imageUrl?.let {
                ImageHelper.loadImage(it, itemView, itemView.cc_iv_icon)
            }

            card.ctaList?.get(0)?.let { cta ->
                itemView.cc_bt_action_first.setBackgroundColor(Color.parseColor(cta.bgColor))
                itemView.cc_bt_action_first.text = cta.text
                itemView.cc_bt_action_first.setTextColor(Color.parseColor(cta.textColor))
                itemView.cc_bt_action_first.setOnClickListener {
                    if (cta.url != null) {
                        launchAction(cta.url)
                    } else if (cta.otherUrl != null) {
                        launchAction(cta.otherUrl)
                    }
                }
            }

            card.ctaList?.get(1)?.let { cta ->
                itemView.cc_bt_action_second.setBackgroundColor(Color.parseColor(cta.bgColor))
                itemView.cc_bt_action_second.text = cta.text
                itemView.cc_bt_action_second.setTextColor(Color.parseColor(cta.textColor))
                itemView.cc_bt_action_second.setOnClickListener {
                    if (cta.url != null) {
                        launchAction(cta.url)
                    } else if (cta.otherUrl != null) {
                        launchAction(cta.otherUrl)
                    }
                }
            }
        }

        /**
         * To bind the data and view for 'Small Cards'
         *
         * @param card an instance [Card] which contains the data fetched from the APIs.
         */
        fun bindSmallCard(card: Card) {
            TextFormatter.applyFormattedText(
                card.formattedTitle, itemView.sdc_tv_title, card.title
            )

            TextFormatter.applyFormattedText(
                card.formattedDescription, itemView.sdc_tv_description, card.description
            )

            itemView.sdc_linear_layout.setBackgroundColor(Color.parseColor(card.bgColor))

            card.icon?.imageUrl?.let {
                ImageHelper.loadImage(it, itemView, itemView.sdc_iv_icon)
            }

            itemView.sdc_card_view.setOnClickListener {
                if (card.url != null) {
                    launchAction(card.url)
                }
            }
        }

        /**
         * This method creates [Intent] to handle "Call To Action" functionality
         *
         * @param url the url or deep link fetched from the API for a specified card
         */
        private fun launchAction(url: String): Boolean {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            ContextualCardApplication.getContext().startActivity(intent)
            return true
        }

    }
}