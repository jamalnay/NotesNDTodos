package com.jamaln.notesndtodos.di

import android.content.Context
import com.jamaln.notesndtodos.data.local.datasources.NotesDatabase
import com.jamaln.notesndtodos.data.local.repository.NoteRepositoryImpl
import com.jamaln.notesndtodos.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): NotesDatabase {
        return NotesDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideNoteRepository(database: NotesDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao())
    }

}