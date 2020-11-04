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

        // setting up the main recycler view
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

    /**
     * This method is used to setup observer for 'successfulFetch' live data and depending upon the
     * value, modify the view.
     * If value of 'successfulFetch' live data is true, data is displayed to the user, else a
     * self-descriptive error screen with a message is displayed.
     */
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

    /**
     * This method is used to modify visibility of various view components to show a
     * shimmering loading screen to the user while data is being fetched from the API.
     */
    private fun showLoadingScreen() {
        swipe_refresh.isRefreshing = false
        err_layout.visibility = View.GONE
        rv_card_groups.visibility = View.GONE
        shimmer_recycler_view.visibility = View.VISIBLE
    }

    /**
     * This method to show the data to the user in the form of a recycler view and hide all other
     * layouts. This method is called once the data has been successfully from the API.
     *
     * @param cardGroups [List] of [CardGroup] that is fetched from the API and it is passed to the
     * adapted so that the data can be bound with the views
     */
    private fun setData(cardGroups: List<CardGroup>) {
        swipe_refresh.isRefreshing = false
        err_layout.visibility = View.GONE
        shimmer_recycler_view.visibility = View.GONE
        rv_card_groups.visibility = View.VISIBLE
        cardGroupAdapter.setGroupData(cardGroups as ArrayList<CardGroup>)
    }

    /**
     * This method is called when any errors occur while attempting to fetch data from API. This
     * method modifies the visibility of view components to show an error screen to the user.
     *
     * @param errorMessage the message that is to be displayed to the user describing the cause
     * of the failure in an abstracted manner.
     */
    private fun showErrorScreen(errorMessage: String) {
        swipe_refresh.isRefreshing = false
        shimmer_recycler_view.visibility = View.GONE
        rv_card_groups.visibility = View.GONE
        err_layout.visibility = View.VISIBLE
        error_layout_tv_message.text = errorMessage
    }

    override fun onDestroy() {
        super.onDestroy()
        cardGroupViewModel.successfulFetch.removeObservers(this)
        cardGroupViewModel.successfulFetch.value = null
    }
}