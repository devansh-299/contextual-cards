package com.devansh.contextualcards.util

import androidx.preference.PreferenceManager
import com.devansh.contextualcards.ContextualCardApplication

/**
 * Helper class to manage storing and fetching data using Shared Preferences
 */
object PreferenceHelper {

    private const val PREF_KEY = "contextual_card_pref"

    /**
     * Helper method to save groupId locally using a string of Id(s) separated by ','
     *
     * @param groupId a unique identifier of group to be excluded.
     */
    fun addGroupId(groupId: String) {
        var storedString: String
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(ContextualCardApplication.getContext())
        if (preferences != null) {
            storedString = preferences.getString(PREF_KEY, "").toString()
            storedString = storedString.plus(",").plus(groupId)
            val editor = preferences.edit()
            editor.putString(PREF_KEY, storedString)
            editor.apply()
        }
    }

    /**
     * Helper method to check if specific group has to be excluded or not
     *
     * @param groupId a unique identifier of the group which may/maynot be excluded
     * @return true if the group has to be excluded, false otherwise
     */
    fun excludeGroup(groupId: String): Boolean {
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(ContextualCardApplication.getContext())
        if (preferences != null) {
            val storedString = preferences.getString(PREF_KEY, "")
            val stringList = storedString?.split(",")
            if (stringList != null) {
                for (id in stringList) {
                    if (id == groupId) {
                        return true
                    }
                }
            } else {
                return false
            }
        }
        return false
    }
}