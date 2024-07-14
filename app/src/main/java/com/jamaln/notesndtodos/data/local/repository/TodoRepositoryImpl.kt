package com.jamaln.notesndtodos.data.local.repository

import com.jamaln.notesndtodos.data.local.dao.TodoDao
import com.jamaln.notesndtodos.data.model.Todo
import com.jamaln.notesndtodos.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor( private val todoDao: TodoDao) : TodoRepository {
    override fun getAllTodos(): Flow<List<Todo>> {
        return todoDao.getAllTodos()
    }

    override suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }

    override suspend fun searchTodos(query: String): Flow<List<Todo>?> {
        return todoDao.searchTodos(query)
    }

}