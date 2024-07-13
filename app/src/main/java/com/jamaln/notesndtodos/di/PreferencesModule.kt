package com.jamaln.notesndtodos.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jamaln.notesndtodos.data.local.datasources.PreferencesDatastore.Companion.preferences
import com.jamaln.notesndtodos.data.local.repository.PreferencesRepositoryImpl
import com.jamaln.notesndtodos.domain.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideUserDataStorePreferences(
        @ApplicationContext applicationContext: Context,
    ): DataStore<Preferences> {
        return applicationContext.preferences
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(
        preferences: DataStore<Preferences>,
        @DispatchersModule.IoDispatcher iODispatcher: CoroutineDispatcher,
    ): PreferencesRepository {
        return PreferencesRepositoryImpl(preferences,iODispatcher)
    }

}