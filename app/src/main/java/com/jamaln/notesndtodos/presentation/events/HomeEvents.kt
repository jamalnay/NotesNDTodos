package com.jamaln.notesndtodos.presentation.events

import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.data.model.Todo
import com.jamaln.notesndtodos.presentation.state.Tabs

sealed class HomeEvents {
    data object GetAllNotes : HomeEvents()
    data object GetAllTags : HomeEvents()
    data object GetAllTodos : HomeEvents()
    data class OnCheckTodo(val todo:Todo) : HomeEvents()
    data class OnSaveTodo(val todo:Todo) : HomeEvents()
    data class OnTagSelected(val tag:Tag) : HomeEvents()
    data class OnSearchQueryChange(val query: String) : HomeEvents()
    data object OnSearchQueryClear : HomeEvents()
    data object OnToggleDarkMode : HomeEvents()
    data object OnGetDarkModeState : HomeEvents()
    data class OnSelectTab(val tab: Tabs) : HomeEvents()
    data class OnTodoTitleChange(val title: String): HomeEvents()
    data class OnTodoDescriptionChange(val description: String): HomeEvents()
    data class OnEditTodo(val todo: Todo):HomeEvents()
    data object CountUncheckedTodos: HomeEvents()
}