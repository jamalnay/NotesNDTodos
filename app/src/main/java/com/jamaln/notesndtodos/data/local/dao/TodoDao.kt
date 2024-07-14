package com.jamaln.notesndtodos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jamaln.notesndtodos.data.model.Todo
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {
    @Transaction
    @Query("SELECT * FROM todos")
    fun getAllTodos(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo): Long

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todos WHERE todo_title LIKE '%' || :query || '%' OR todo_description LIKE '%' || :query || '%'")
    fun searchTodos(query: String): Flow<List<Todo>?>

    @Query("SELECT COUNT(*) FROM todos WHERE is_checked = 0")
    fun countCheckedTodos(): Flow<Int>
}