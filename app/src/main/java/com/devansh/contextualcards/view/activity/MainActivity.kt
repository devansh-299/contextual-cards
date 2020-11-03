package com.devansh.contextualcards.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devansh.contextualcards.R
import com.devansh.contextualcards.model.CardGroup
import com.devansh.contextualcards.view.adapter.CardGroupAdapter
import com.devansh.contextualcards.viewmodel.CardGroupViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var cardGroupAdapter: CardGroupAdapter
    private val cardGroupViewModel by lazy {
        ViewModelProvider(this).get(CardGroupViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardGroupAdapter = CardGroupAdapter(this)

        // TODO("have another look at this")
        rv_card_groups.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cardGroupAdapter
        }

        cardGroupViewModel.fetchCards()
        getData()
    }

    private fun getData() {
        showLoadingScreen()
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
        // TODO("Not yet implemented")
    }

    private fun setData(cardGroups: List<CardGroup>) {
        cardGroupAdapter.setGroupData(cardGroups as ArrayList<CardGroup>)
    }

    private fun showErrorScreen(errorMessage: String) {
        // TODO("set error message feature not added yet")
    }
}