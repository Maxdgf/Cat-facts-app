package com.maxdgf.top15randomcatfacts.presentation.main_screen.utils

import android.content.Context
import android.content.Intent

class ShareManager(private val context: Context) {
    /** Shares text message.
     * @return success state */
    fun shareMessage(message: String): Boolean {
        // create intent for sharing
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        val intentChooser = Intent.createChooser(intent, null) // create chooser from intent(for sharing)

        // check if there is a component capable of handling intent
        return if (intentChooser.resolveActivity(context.packageManager) != null) {
            context.startActivity(intentChooser) // launch chooser
            true // return true, chooser started and user have ability to share text message
        } else false // return false, chooser not launched
    }
}
