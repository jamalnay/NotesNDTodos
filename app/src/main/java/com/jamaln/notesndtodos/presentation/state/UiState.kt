package com.jamaln.notesndtodos.presentation.state

import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.data.model.TagWithNotes


class UiState {
    data class NotesListState(val notes : TagWithNotes = TagWithNotes( Tag("All"),emptyList()))

    data class TagsState(
        val tags: List<Tag> = emptyList(),
        val selectedTag: Tag = Tag("All"),
    )

    data class DarkModeState(val isInDarkMode: Boolean = false)

    data class SearchBarState(val searchQuery: String = "", )

    data class TabState(val selectedTab: Tabs = Tabs.Notes)

    enum class Tabs { Notes, Todos }
}