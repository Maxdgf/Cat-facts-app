package com.maxdgf.top15randomcatfacts.domain.repository

import kotlinx.coroutines.flow.Flow

interface SelectedLanguageSource {
    /** Returns current selected language code for facts from data store. */
    fun getSelectedLanguageCode(): Flow<String>

    /** Saves selected language code to data store. */
    suspend fun saveSelectedLanguageCode(code: String)
}