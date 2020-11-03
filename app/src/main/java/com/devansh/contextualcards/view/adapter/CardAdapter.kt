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
import com.devansh.contextualcards.model.Card
import kotlinx.android.synthetic.main.item_big_display_card.view.*


class CardAdapter :  RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cardData: ArrayList<Card> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_big_display_card, parent, false))
    }

    override fun getItemCount(): Int {
        return cardData.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindData(cardData[position])
    }

    fun setCardData(cards: ArrayList<Card>) {
        cardData.clear()
        cardData.addAll(cards)
        notifyDataSetChanged()
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val noteTitle = itemView.tv_title
        private val noteDescription = itemView.tv_description
        private val cardImage = itemView.card_view

        fun bindData(card: Card) {
            noteTitle.text = card.title
            noteDescription.text = card.description
            Glide.with(itemView)
                .load(card.bgImage?.imageUrl)
                .into(object :
                    CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                        TODO("Not yet implemented")
                    }
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?) {
                        cardImage.background=resource
                    }
                })
        }
    }
}