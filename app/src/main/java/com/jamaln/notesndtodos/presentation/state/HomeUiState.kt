package com.jamaln.notesndtodos.presentation.state

import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.utils.Constants.ALL_NOTES_TAG


class HomeUiState {
    data class NotesListState(val notes : List<Note> = emptyList())

    data class TagsState(
        val tags: List<Tag> = emptyList(),
        val selectedTag: Tag = ALL_NOTES_TAG,
    )

    data class SearchBarState(val searchQuery: String = "" )

    data class TabState(val selectedTab: Tabs = Tabs.Notes)

    enum class Tabs { Notes, Todos }
}