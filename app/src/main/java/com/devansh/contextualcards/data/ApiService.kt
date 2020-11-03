package com.devansh.contextualcards.data

import com.devansh.contextualcards.model.CardGroup
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {
    @GET
    fun fetchCardGroups (): Single<List<CardGroup>>
}