package com.jamaln.notesndtodos.data.local.datasources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jamaln.notesndtodos.data.local.dao.TodoDao
import com.jamaln.notesndtodos.data.model.Todo

const val TODO_DATABASE_NAME = "todo_database"


@Database(
    entities = [Todo::class],
    version = 1,
    exportSchema = false)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var instance: TodoDatabase? = null
        fun getInstance(context: Context): TodoDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): TodoDatabase {
            return Room.databaseBuilder(context, TodoDatabase::class.java, TODO_DATABASE_NAME)
                .build()
        }
    }
}