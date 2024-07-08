package com.jamaln.notesndtodos.presentation.home

import com.jamaln.notesndtodos.data.model.Tag

sealed class HomeEvents {
    data object GetAllNotes : HomeEvents()
    data object GetAllTags : HomeEvents()
    data class GetNotesForTag(val tag:Tag) : HomeEvents()
}