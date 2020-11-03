package com.devansh.contextualcards.data

import com.devansh.contextualcards.model.CardGroup
import io.reactivex.rxjava3.core.Single

class Repository {

    private val apiManager = ApiManager.instance

    fun getCardGroups(): Single<List<CardGroup>> {
        return apiManager.apiService.fetchCardGroups()
    }

}