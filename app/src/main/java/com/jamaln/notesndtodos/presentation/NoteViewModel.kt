package com.jamaln.notesndtodos.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.domain.repository.NoteRepository
import com.jamaln.notesndtodos.presentation.events.NoteEvents
import com.jamaln.notesndtodos.presentation.state.NoteUiState
import com.jamaln.notesndtodos.utils.Constants.ALL_NOTES_TAG
import com.jamaln.notesndtodos.utils.NoteUtils.Companion.capitalizeFirstLetter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

const val NOTE_VIEWMODEL = "NoteViewModel"
@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
):ViewModel() {

    private val _noteState = MutableStateFlow(NoteUiState.NoteState())
    val noteState = _noteState.asStateFlow()

    private val _noteEditingState = MutableStateFlow(NoteUiState.NoteEditingState())
    val noteEditingState = _noteEditingState.asStateFlow()

    private val _titleState = MutableStateFlow(NoteUiState.NoteTitleState())
    val titleState = _titleState.asStateFlow()

    private val _contentTextState = MutableStateFlow(NoteUiState.NoteContentState())
    val contentTextState = _contentTextState.asStateFlow()

    private val _tagsState = MutableStateFlow(NoteUiState.NoteTagsState())
    val tagsState = _tagsState.asStateFlow()

    private val _selectedTagsState = MutableStateFlow(NoteUiState.SelectedTagsState())
    val selectedTagsState = _selectedTagsState.asStateFlow()

    private val _newTagState = MutableStateFlow(NoteUiState.NewTagState())
    val newTagState = _newTagState.asStateFlow()

    private val _deleteDialogState = MutableStateFlow(NoteUiState.NoteDeleteState())
    val deleteDialogState = _deleteDialogState.asStateFlow()


    fun onEvent(event: NoteEvents){
        when(event){
            is NoteEvents.OnCreateNewTag -> onCreateNewTag()
            is NoteEvents.OnConfirmDeleteNote -> deleteNote(event.note)
            is NoteEvents.OnGetNote -> getNote(noteId = event.noteId)
            is NoteEvents.OnSelectTag -> onSelectTag(tag = event.tag)
            is NoteEvents.OnUnselectTag -> onUnselectTag(tag = event.tag)
            is NoteEvents.OnContentTextChange -> onContentTextChange(contentText = event.contentText)
            is NoteEvents.OnSaveChanges -> onSaveChanges(note = event.note, tags = event.tags)
            is NoteEvents.OnTitleChange -> onTitleChange(title = event.title)
            is NoteEvents.OnEditNote -> onEditNote(isEditing = event.isEditing)
            is NoteEvents.OnCancelCreatingNewTag -> onCancelCreatingNewTag()
            is NoteEvents.OnSaveNewTag -> onSaveNewTag(tagName = event.tagName)
            is NoteEvents.OnNewTagNameChange -> onNewTagNameChange(event.tagName)
            is NoteEvents.OnDeleteClick -> onDeleteClick()
            is NoteEvents.OnCancelDelete -> onCancelDelete()
            is NoteEvents.OnNewNote -> onNewNote()
        }
    }

    private fun onUnselectTag(tag: Tag) {
        _selectedTagsState.value = selectedTagsState.value.copy(tags = selectedTagsState.value.tags.minus(tag))
        Log.d(NOTE_VIEWMODEL, "onUnselectTag: ${selectedTagsState.value.tags}")
    }

    private fun onSelectTag(tag: Tag) {
        _selectedTagsState.value = selectedTagsState.value.copy(tags = selectedTagsState.value.tags.plus(tag))
        Log.d(NOTE_VIEWMODEL, "onSelectTag: ${selectedTagsState.value.tags}")
    }

    private fun onCancelDelete() {
        _deleteDialogState.value = deleteDialogState.value.copy(isDeleteDialogShown = false)
    }

    private fun onDeleteClick() {
        _deleteDialogState.value = deleteDialogState.value.copy(isDeleteDialogShown = true)
    }

    private fun onNewTagNameChange(tagName: String) {
        _newTagState.value = newTagState.value.copy(tagName = tagName)
    }

    private fun onCancelCreatingNewTag() {
        _newTagState.value = newTagState.value.copy(tagName = "", isNewTagDialogShown = false)
    }

    private fun onCreateNewTag() {
        _newTagState.value = newTagState.value.copy(isNewTagDialogShown = true)
    }

    private fun onEditNote(isEditing:Boolean) {
        _noteEditingState.value = noteEditingState.value.copy(isEditingNote = isEditing)
    }

    private fun onTitleChange(title: String) {
        _titleState.value = titleState.value.copy(title = title)
    }

    private fun onContentTextChange(contentText: String) {
        _contentTextState.value = contentTextState.value.copy(noteContentText = contentText)
    }

    private fun onSaveChanges(note: Note, tags: List<Tag>) {
        viewModelScope.launch {
            //we must delete the old tags associated with this note and replace them with new list of tags
            noteRepository.deleteNoteWithTags(note.noteId)
            noteRepository.insertNoteWithTags(note, tags.plus(ALL_NOTES_TAG))
            _tagsState.value = tagsState.value.copy(tags = tags)
            Log.d(NOTE_VIEWMODEL, "onSaveChanges: $tags")
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
            _deleteDialogState.value = deleteDialogState.value.copy(isDeleteDialogShown = false)
        }
    }

    private fun onSaveNewTag(tagName: String) {
        _noteEditingState.value = noteEditingState.value.copy(isEditingNote = true)
        _tagsState.value = tagsState.value.copy(tags = tagsState.value.tags.plus(Tag(capitalizeFirstLetter(tagName))))
        _newTagState.value = newTagState.value.copy(tagName = "", isNewTagDialogShown = false)
        _selectedTagsState.value = _selectedTagsState.value.copy(tags = _selectedTagsState.value.tags.plus(Tag(capitalizeFirstLetter(tagName))))
    }

    private fun getNote(noteId: Int) {
        viewModelScope.launch {
            val noteWithTags = noteRepository.getNoteWithTags(noteId)
            if (noteWithTags != null){
                _noteState.value = noteState.value.copy(note = noteWithTags.note)
                _tagsState.value = tagsState.value.copy(tags = noteWithTags.tags)
                _selectedTagsState.value = selectedTagsState.value.copy(tags = tagsState.value.tags)
                _titleState.value = titleState.value.copy(title = noteWithTags.note.title)
                _contentTextState.value = contentTextState.value.copy(noteContentText = noteWithTags.note.contentText)
            }
        }
    }
    private fun onNewNote(){
        _noteEditingState.value = noteEditingState.value.copy(isEditingNote = true)
    }
}