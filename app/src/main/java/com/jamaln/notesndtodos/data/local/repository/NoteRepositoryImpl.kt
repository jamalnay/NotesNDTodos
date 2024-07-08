package com.jamaln.notesndtodos.data.local.repository

import com.jamaln.notesndtodos.data.local.dao.NoteDao
import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.NoteWithTags
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.data.model.TagWithNotes
import com.jamaln.notesndtodos.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao):NoteRepository {

    override suspend fun insertNoteWithTags(note: Note, tags: List<Tag>) {
        noteDao.insertNoteWithTags(note, tags)
    }

    override fun getNoteWithTags(noteId: Int): Flow<List<NoteWithTags>>{
        return noteDao.getNoteWithTags(noteId)
    }

    override fun getTagWithNotes(tagName: String): Flow<List<TagWithNotes>> {
        return noteDao.getTagWithNotes(tagName)
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override fun getAllTags(): Flow<List<Tag>> {
        return noteDao.getAllTags()
    }

    // Retrieve only tags that have notes associated with them
    override fun getTagsWithNotes(): Flow<List<Tag>> {
        return noteDao.getTagsWithNotes()
    }
}