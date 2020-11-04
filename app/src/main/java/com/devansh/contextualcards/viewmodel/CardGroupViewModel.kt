package com.devansh.contextualcards.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devansh.contextualcards.ContextualCardApplication
import com.devansh.contextualcards.R
import com.devansh.contextualcards.data.Repository
import com.devansh.contextualcards.model.CardGroup
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException


class CardGroupViewModel : ViewModel() {
    private val tag = CardGroupViewModel::class.java.simpleName
    private val repository: Repository = Repository()
    val successfulFetch: MutableLiveData<Boolean> = MutableLiveData()

    lateinit var cardGroups: List<CardGroup>
    lateinit var errorMessage: String

    fun fetchCards() {
        repository.getCardGroups()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<CardGroup>>() {
                override fun onSuccess(groups: List<CardGroup>) {
                    cardGroups = groups
                    successfulFetch.value = true
                    Log.d(tag, "API call successful")
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
            })
    }

}