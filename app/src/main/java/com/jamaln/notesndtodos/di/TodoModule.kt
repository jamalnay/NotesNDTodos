package com.jamaln.notesndtodos.di

import android.content.Context
import com.jamaln.notesndtodos.data.local.datasources.TodoDatabase
import com.jamaln.notesndtodos.data.local.repository.TodoRepositoryImpl
import com.jamaln.notesndtodos.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return TodoDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideTodoRepository(database: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(database.todoDao())
    }

}