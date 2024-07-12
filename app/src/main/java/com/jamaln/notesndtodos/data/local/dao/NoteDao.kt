package com.jamaln.notesndtodos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jamaln.notesndtodos.data.model.Note
import com.jamaln.notesndtodos.data.model.NoteTagCrossRef
import com.jamaln.notesndtodos.data.model.NoteWithTags
import com.jamaln.notesndtodos.data.model.Tag
import com.jamaln.notesndtodos.data.model.TagWithNotes
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTagCrossRef(noteTagCrossRef: NoteTagCrossRef)

    // Insert a note with tags
    @Transaction
    suspend fun insertNoteWithTags(note: Note, tags: List<Tag>) {
        val noteId = insertNote(note)
        tags.forEach { tag ->
            insertTag(tag)
            insertNoteTagCrossRef(NoteTagCrossRef(noteId.toInt(), tag.tagName))
        }
    }

    @Transaction
    @Query("SELECT * FROM notes WHERE note_id = :noteId")
    suspend fun getNoteWithTags(noteId: Int): NoteWithTags?


    @Transaction
    @Query("SELECT * FROM tags WHERE tag_name = :tagName")
    suspend fun getTagWithNotes(tagName: String): TagWithNotes?


    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<Note>>


    @Query("SELECT * FROM tags")
    fun getAllTags(): Flow<List<Tag>>

    // Retrieve only tags that have notes associated with them
    @Query("""
        SELECT DISTINCT tags.*
        FROM tags
        INNER JOIN NoteTagCrossRef ON tags.tag_name = NoteTagCrossRef.tag_name
        INNER JOIN notes ON notes.note_id = NoteTagCrossRef.note_id
    """)
    fun getTagsWithNotes(): Flow<List<Tag>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content_text LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<Note>?>

    @Delete
    suspend fun deleteNote(note: Note)

    @Transaction
    @Query("DELETE FROM NoteTagCrossRef WHERE note_id = :noteId")
    suspend fun deleteNoteWithTags(noteId:Int)

}