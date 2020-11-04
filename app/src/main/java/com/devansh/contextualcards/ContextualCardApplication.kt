package com.devansh.contextualcards

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class ContextualCardApplication : Application() {

    companion object {

        lateinit var instance: ContextualCardApplication

        /**
         * @return the instance of the Application
         */
        fun getApplication(): ContextualCardApplication {
            return instance
        }

        /**
         * @return the context of the Application
         */
        fun getContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}