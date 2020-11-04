package com.devansh.contextualcards.util

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.devansh.contextualcards.model.FormattedText

/**
 * Util class to handle all text formatting that needs to be done in the application's codebase.
 */
class TextFormatter {

    companion object {
        /**
         *
         * @param formattedText an instance of [FormattedText] which specifies simple text and for the
         * formatting details.
         *@param textView instance of [TextView] into which the formatted/resultant text is
         * to be set.
         * @param callBackText a simple string which is passed as a back-up in case any issues
         * are raised while parsing [FormattedText] instance.
         */
        fun applyFormattedText(
            formattedText: FormattedText?,
            textView: TextView,
            callBackText: String?
        ) {

            if (formattedText == null) {
                textView.text = callBackText
            } else {
                val spannableStringBuilder = SpannableStringBuilder()
                for (index in formattedText.entityList.indices) {
                    val entity = formattedText.entityList[index]
                    val colorCode = Color.parseColor(formattedText.entityList[index].color)
                    var spannableString = SpannableString("${entity.text} ")

                    spannableString.setSpan(
                        ForegroundColorSpan(colorCode),
                        0,
                        entity.text.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannableStringBuilder.append(spannableString)
                }
                if (spannableStringBuilder.isEmpty()) {
                    textView.text = callBackText
                } else {
                    textView.text = spannableStringBuilder
                }
            }
        }
    }
}