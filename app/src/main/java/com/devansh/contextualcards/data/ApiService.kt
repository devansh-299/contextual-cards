package com.devansh.contextualcards.data

import com.devansh.contextualcards.data.ApiHelper.ENDPOINT
import com.devansh.contextualcards.model.CardGroup
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiService {
    @GET(ENDPOINT)
    fun fetchCardGroups(): Observable<List<CardGroup>>
}