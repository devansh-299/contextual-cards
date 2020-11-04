package com.devansh.contextualcards.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.devansh.contextualcards.R
import com.devansh.contextualcards.model.CardGroup
import com.devansh.contextualcards.view.adapter.CardGroupAdapter
import com.devansh.contextualcards.viewmodel.CardGroupViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var cardGroupAdapter: CardGroupAdapter
    private val cardGroupViewModel by lazy {
        ViewModelProvider(this).get(CardGroupViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardGroupAdapter = CardGroupAdapter(this)

        rv_card_groups.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cardGroupAdapter
        }
        swipe_refresh.setOnRefreshListener(this)
        getData()
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun onRefresh() {
        showLoadingScreen()
        cardGroupViewModel.fetchCards()
    }

    private fun getData() {
        cardGroupViewModel.successfulFetch.observe(this, Observer { successful ->
            if (successful != null && successful is Boolean) {
                if (successful) {
                    setData(cardGroupViewModel.cardGroups)
                } else {
                    showErrorScreen(cardGroupViewModel.errorMessage)
                }
            }
        })
    }

    private fun showLoadingScreen() {
        swipe_refresh.isRefreshing = false
        err_layout.visibility = View.GONE
        rv_card_groups.visibility = View.GONE
        shimmer_recycler_view.visibility = View.VISIBLE
    }

    private fun setData(cardGroups: List<CardGroup>) {
        swipe_refresh.isRefreshing = false
        err_layout.visibility = View.GONE
        shimmer_recycler_view.visibility = View.GONE
        rv_card_groups.visibility = View.VISIBLE
        cardGroupAdapter.setGroupData(cardGroups as ArrayList<CardGroup>)
    }

    private fun showErrorScreen(errorMessage: String) {
        swipe_refresh.isRefreshing = false
        shimmer_recycler_view.visibility = View.GONE
        rv_card_groups.visibility = View.GONE
        err_layout.visibility = View.VISIBLE
        error_layout_tv_message.text = errorMessage
    }
}