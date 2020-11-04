package com.devansh.contextualcards.data

import com.devansh.contextualcards.model.CardGroup
import io.reactivex.rxjava3.core.Observable

/**
 * A layer of abstraction over the core data layer components and provides an interaction point
 * between the caller (view models) and the API services
 */
class Repository {

    private val apiManager = ApiManager.instance

    /**
     * used to fetch card groups
     *
     * @return an [Observable] of list of card groups fetched from the API.
     */
    fun getCardGroups(): Observable<List<CardGroup>> {
        return apiManager.apiService.fetchCardGroups()
    }
}