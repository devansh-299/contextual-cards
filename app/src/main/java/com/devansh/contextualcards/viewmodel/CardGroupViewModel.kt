package com.devansh.contextualcards.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devansh.contextualcards.ContextualCardApplication
import com.devansh.contextualcards.R
import com.devansh.contextualcards.data.Repository
import com.devansh.contextualcards.model.CardGroup
import com.devansh.contextualcards.util.PreferenceHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException


class CardGroupViewModel : ViewModel() {
    private val tag = CardGroupViewModel::class.java.simpleName
    private val repository: Repository = Repository()

    /**
     * LiveData to keep track of the fetching process' status.
     * True if successfully fetched, false otherwise.
     */
    val successfulFetch: MutableLiveData<Boolean> = MutableLiveData()

    // To store the fetched CardGroup list
    lateinit var cardGroups: List<CardGroup>

    // To store any messages that may need to be displayed to the user
    lateinit var errorMessage: String

    /**
     * This method is used to make calls to API to fetch the list of [CardGroup]
     * by calling methods exposed by [Repository]. All the fetching is being on a back-ground
     * while only results are being observed/registered by the UI thread.
     */
    fun fetchCards() {
        repository.getCardGroups()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<CardGroup>>() {
                override fun onNext(groups: List<CardGroup>) {
                    cardGroups = getFilteredList(groups)
                    successfulFetch.value = true
                    Log.d(tag, "successfully fetched")
                }

                override fun onError(throwable: Throwable) {
                    when (throwable) {
                        is IOException -> {
                            errorMessage = ContextualCardApplication
                                .getContext().getString(R.string.check_connection)
                        }
                        is TimeoutException -> {
                            errorMessage = ContextualCardApplication
                                .getContext().getString(R.string.time_out)
                        }
                        is HttpException -> {
                            errorMessage = ContextualCardApplication
                                .getContext().getString(R.string.cannot_connect_server)
                        }
                        else -> {
                            throwable.message?.let { Log.e(tag, it) }
                            errorMessage = ContextualCardApplication
                                .getContext().getString(R.string.error_occurred)
                        }
                    }
                    successfulFetch.value = false
                }

                override fun onComplete() {}
            })
    }

    /**
     * This method is used to exclude any group whose id is stored in local storage
     */
    // TODO: do this task in back-ground thread
    private fun getFilteredList(groups: List<CardGroup>): List<CardGroup> {
        val filteredList = ArrayList<CardGroup>()
        for (group in groups) {
            if (!PreferenceHelper.excludeGroup(group.id.toString())) {
                filteredList.add(group)
            }
        }
        return filteredList
    }

}