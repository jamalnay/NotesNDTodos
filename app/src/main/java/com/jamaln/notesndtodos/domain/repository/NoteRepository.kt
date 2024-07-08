package com.jamaln.notesndtodos.domain.repository

import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.NoteWithTags
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.data.model.TagWithNotes
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insertNoteWithTags(note: Note, tags: List<Tag>)

    fun getNoteWithTags(noteId: Int): Flow<List<NoteWithTags>>

    fun getTagWithNotes(tagName: String): Flow<List<TagWithNotes>>

    fun getAllNotes(): Flow<List<Note>>

    fun getAllTags(): Flow<List<Tag>>

    // Retrieve only tags that have notes associated with them
    fun getTagsWithNotes(): Flow<List<Tag>>
}