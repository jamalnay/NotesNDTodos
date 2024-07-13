package com.jamaln.notesndtodos.data.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.jamaln.notesndtodos.domain.repository.PreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
        private val preferences: DataStore<Preferences>,
        private val iODispatcher: CoroutineDispatcher
    ): PreferencesRepository {

    override suspend fun toggleDarkLight() {
        preferences.edit { preferences ->
            val currentMode = preferences[PreferencesRepository.IS_DARK_THEME] ?: false
            preferences[PreferencesRepository.IS_DARK_THEME] = !currentMode
        }
    }

    override fun isDarkTheme(): Flow<Boolean> {
       return preferences.data.map {
           preferences-> preferences[PreferencesRepository.IS_DARK_THEME] ?: false
       }.flowOn(iODispatcher)
    }
}