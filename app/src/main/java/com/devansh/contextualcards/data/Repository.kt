package com.devansh.contextualcards.data

import com.devansh.contextualcards.model.CardGroup
import io.reactivex.rxjava3.core.Observable

class Repository {

    private val apiManager = ApiManager.instance

    fun getCardGroups(): Observable<List<CardGroup>> {
        return apiManager.apiService.fetchCardGroups()
    }

}