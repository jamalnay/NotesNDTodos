package com.jamaln.notesndtodos.presentation.state

import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.Tag

class NoteUiState {
    data class NoteState(
        val note: Note = Note(0,"","", emptyList(), emptyList(), System.currentTimeMillis())
    )
    data class NoteTitleState(val title: String = "")
    data class NoteContentState(val noteContentText: String = "")
    data class NoteEditingState(val isEditingNote: Boolean = false)
    data class NoteTagsState(val tags: List<Tag> = emptyList())
    data class SelectedTagsState(val tags: List<Tag> = emptyList())
    data class NewTagState(val tagName: String = "",val isNewTagDialogShown: Boolean = false)
    data class NoteDeleteState(val isDeleteDialogShown: Boolean = false)
}