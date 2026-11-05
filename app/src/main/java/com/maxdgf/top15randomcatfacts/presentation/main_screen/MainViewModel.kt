package com.maxdgf.top15randomcatfacts.presentation.main_screen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.maxdgf.top15randomcatfacts.domain.repository.CatFactsRepository
import com.maxdgf.top15randomcatfacts.domain.repository.SelectedLanguageSource


class MainViewModel(
    private val catFactsRepository: CatFactsRepository,
    private val selectedLanguageSource: SelectedLanguageSource
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val selectedLanguage = selectedLanguageSource.getSelectedLanguageCode()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = "eng"
        )

    private val _allCatFacts = MutableStateFlow<CatFactsResult>(CatFactsResult.Initial)
    val allCatFacts = _allCatFacts.asStateFlow()

    init { loadCatFacts() } // load cat facts initially

    /** Loads cat facts list. */
    fun loadCatFacts() = viewModelScope.launch(Dispatchers.IO) {
        try {
            _isRefreshing.update { true }

            val languageCode = selectedLanguageSource.getSelectedLanguageCode().first() // get current selected language code
            val facts = catFactsRepository.getRandomCatsFacts(languageCode) // load cat facts by selected language code

            _allCatFacts.update { CatFactsResult.Content(facts.data) }
        } catch (e: Exception) {
            _allCatFacts.update { CatFactsResult.Error(e.message) }
        } finally {
            _isRefreshing.update { false }
        }
    }

    /** Selects facts language by code. */
    fun selectLanguage(code: String) = viewModelScope.launch(Dispatchers.IO) {
        selectedLanguageSource.saveSelectedLanguageCode(code)
    }
}