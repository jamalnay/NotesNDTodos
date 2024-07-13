package com.jamaln.notesndtodos.presentation.events

import com.jamaln.notesndtodos.data.model.Tag

sealed class HomeEvents {
    data object GetAllNotes : HomeEvents()
    data object GetAllTags : HomeEvents()
    data class GetNotesForTag(val tag:Tag) : HomeEvents()
    data class OnSearchQueryChange(val query: String) : HomeEvents()
    data object OnSearchQueryClear : HomeEvents()
}