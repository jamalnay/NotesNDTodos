package com.jamaln.notesndtodos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jamaln.notesndtodos.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DarkModeState(val isInDarkMode: Boolean = false)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    private val _darkModeState = MutableStateFlow(DarkModeState())
    val darkModeState = _darkModeState.asStateFlow()

    init {
        getDarkModeState()
    }

    private fun getDarkModeState() {
        viewModelScope.launch {
            preferencesRepository.isDarkTheme().collect{ isDarkTheme ->
                _darkModeState.value = darkModeState.value.copy(isInDarkMode = isDarkTheme)
            }
        }
    }

    fun onDarkModeToggle() {
        viewModelScope.launch {
            preferencesRepository.toggleDarkLight()
        }
    }
}