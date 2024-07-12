package com.jamaln.notesndtodos.presentation.state

import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.Tag


class HomeUiState {
    data class NotesListState(val notes : List<Note> = emptyList())

    data class TagsState(
        val tags: List<Tag> = emptyList(),
        val selectedTag: Tag = Tag("All Notes"),
    )

    data class DarkModeState(val isInDarkMode: Boolean = false)

    data class SearchBarState(val searchQuery: String = "" )

    data class TabState(val selectedTab: Tabs = Tabs.Notes)

    enum class Tabs { Notes, Todos }
}