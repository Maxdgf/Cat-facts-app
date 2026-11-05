package com.maxdgf.top15randomcatfacts.presentation.main_screen.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipboardManager(context: Context) {
    private val _clipboard =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager // get clipboard manager

    /** Sets text to clipboard.
     * @param text text string */
    fun setTextToClipboard(text: String) {
        val clipData = ClipData.newPlainText("Copied cat fact.", text) // new clip data
        _clipboard.setPrimaryClip(clipData) // set clip data
    }
}