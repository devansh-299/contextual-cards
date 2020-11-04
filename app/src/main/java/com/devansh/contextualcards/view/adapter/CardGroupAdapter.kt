package com.devansh.contextualcards.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devansh.contextualcards.R
import com.devansh.contextualcards.model.Card
import com.devansh.contextualcards.model.CardGroup

/**
 * The main [RecyclerView] which displays the list of [CardGroup] fetched from the API.
 * Note that for each group a separate [RecyclerView] generated.
 *
 * @param context the calling/parent activity's [Context]
 */
class CardGroupAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var groupData: ArrayList<CardGroup> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.layout_card_group, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groupData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        cardView(holder as CardViewHolder, position)
    }

    private fun cardView(holder: CardViewHolder, position: Int) {
        val cardAdapter = CardAdapter(groupData[position].designType, groupData[position].id)
        holder.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = cardAdapter
        }

        holder.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {
                        cardAdapter.hideMenu()
                    }
                }
            }
        })
        cardAdapter.setCardData(groupData[position].cardList as ArrayList<Card>)
    }

    fun setGroupData(groups: ArrayList<CardGroup>) {
        groupData.clear()
        groupData.addAll(groups)
        notifyDataSetChanged()
    }

    inner class CardViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var recyclerView: RecyclerView = itemView.findViewById<View>(R.id.rv_card) as RecyclerView
    }
}