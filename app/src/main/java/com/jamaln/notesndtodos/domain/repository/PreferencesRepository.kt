package com.jamaln.notesndtodos.domain.repository

import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow


interface PreferencesRepository {
    companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    suspend fun toggleDarkLight()

    fun isDarkTheme(): Flow<Boolean>
}